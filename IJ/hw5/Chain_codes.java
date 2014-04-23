
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;



public class Chain_codes implements PlugInFilter {


	int fg = 255;
	int bg = 0;
	
	HashSet<Contour> chains = new HashSet<Contour>();

	
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor orig_ip) {
		int w = orig_ip.getWidth();
		int h = orig_ip.getHeight();
		
		ImageProcessor ip = orig_ip.duplicate();
		ByteProcessor labelMap = new ByteProcessor(ip.getWidth(), ip.getHeight());
//		ByteProcessor frontier = new ByteProcessor(ip.getWidth(), ip.getHeight());
		
		
		int R = 0;
		
		for(int v = 0; v<h; v++){
			int Lk = 0;
			for(int u = 0; u<w; u++){
				if(ip.getPixel(u, v) == fg) {
					if(Lk != 0){
						labelMap.putPixel(u, v, Lk);
					} else {
						Lk = labelMap.getPixel(u, v);
						if (Lk == 0) {
							R++;
							Lk = R;
							Point xs = new Point(u, v);
							Contour c_outer = TraceContour(xs, 0, Lk, ip, labelMap);
							chains.add(c_outer);
							labelMap.putPixel(u, v, Lk);
						}
					}
				} else {
					if (Lk != 0) {
						if (labelMap.getPixel(u, v) == 0) {
							Point xs = new Point(u-1, v);
							Contour c_inner = TraceContour(xs, 1, Lk, ip, labelMap);
//							System.out.print("Trying to add: " + c_inner + "   ");
							chains.add(c_inner);
						}
						Lk = 0;
					}
				}
			}
		}
		
		for(Contour c : chains){
			System.out.println(c);
			
			System.out.println(c.makeChain(false));
			System.out.println(c.makeChain(true));
			System.out.println(c.getShapeNumber());
			
			for(Point p : c.getPointList()){
				orig_ip.putPixel(p.x, p.y, 128);
			}
		}
		
		System.out.println(chains.iterator().next().makeChain(false));
		System.out.println(chains.iterator().next().makeChain(true));
		System.out.println(chains.iterator().next().getShapeNumber());
		
		ImagePlus win = new ImagePlus("Label Map", labelMap);
		win.show();
	}

	private Contour TraceContour(Point xs, int ds, int lc, ImageProcessor ip, ByteProcessor labelMap) {
		Pair<Point, Integer> nextPoint = FindNextPoint(xs, ds, ip, labelMap);
		Point xt = nextPoint.getFirst();
		Integer d_next = nextPoint.getSecond();
		
		Contour c = new Contour(lc);
		c.addPoint(xt, d_next);
		Point xp = xs;
		Point xc = xt;
		boolean done = xs.equals(xt);
		
		while(!done){
			labelMap.putPixel(xc.x, xc.y, lc);
			int d_search = (d_next +6) % 8;
			Pair<Point, Integer> next = FindNextPoint(xc, d_search, ip, labelMap);
			Point xn = next.getFirst();
			d_next = next.getSecond();
			xp = xc;
			xc = xn;
			done = xp.equals(xs) && xc.equals(xt);
			if(!done) c.addPoint(xn, d_next);
		}
		
		return c;
	}

	private Pair<Point, Integer> FindNextPoint(Point xc, int d,
			ImageProcessor ip, ByteProcessor labelMap) {
		for(int i = 0; i <= 6; i++){
			Point xprime = new Point(xc.x + DELTA(d).x, xc.y + DELTA(d).y);
			if (ip.getPixel(xprime.x, xprime.y) == this.bg){
				labelMap.putPixel(xprime.x, xprime.y, -1);
				d = (d+1) % 8;
			} else {
				return new Pair<Point, Integer>(xprime, d);
			}
		}
		return new Pair<Point, Integer>(xc, d);
	}

	private Point DELTA(int d) {
		switch (d) {
		case 0:
			return new Point( 1,  0);
		case 1:
			return new Point( 1,  1);
		case 2:
			return new Point( 0,  1);
		case 3:
			return new Point(-1,  1);
		case 4:
			return new Point(-1,  0);
		case 5:
			return new Point(-1, -1);
		case 6:
			return new Point( 0, -1);
		case 7:
			return new Point( 1, -1);
		default:
			break;
		}
		return null;
	}
}
