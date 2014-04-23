import java.util.ArrayList;
import java.util.Stack;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class Region_labeling implements PlugInFilter {

	private int visited_val = 255;
	private int unvisited_val = 128;
	
	private int unlabeled_val = 1;
	private int label = 2;

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor orig_ip) {
		
		ImageProcessor ip = orig_ip.duplicate();
		
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ByteProcessor expanded = new ByteProcessor(ip.getWidth(), ip.getHeight());
		for(int u = 0; u<w; u++){
			for(int v = 0; v<h; v++){
				if(ip.getPixel(u, v) != 0){
					ip.putPixel(u, v, unlabeled_val);
					expanded.putPixel( u, v, unvisited_val );
				}
				
			}
		}
		
		ArrayList<ArrayList<Node> > regions = new ArrayList<ArrayList<Node> >();
		ArrayList<Centroid> centroids = new ArrayList<Centroid>();
		
		for(int v = 0; v<h; v++){
			for(int u = 0; u<w; u++){
				if(ip.getPixelValue(u, v) == unlabeled_val){
					ArrayList<Node> r = floodFill(u, v, ip, expanded, label);
					regions.add(r);
					
					Centroid c = Region_Utils.centroid(r);
					centroids.add(c);
					
					System.out.println(label + ": " + Region_Utils.orientation(r));
					Utils.drawCorner(orig_ip, (int)c.x, (int)c.y, 128);
					
					Eccentricity e = Region_Utils.eccentricity(r);
					System.out.println(e);
					Utils.drawVector(orig_ip, (int)c.x, (int)c.y, Region_Utils.orientation(r), e.getRA(), 128);
					
					Utils.drawEllipsoid(orig_ip, (int)c.x, (int)c.y, e.getRA(), e.getRB(), Region_Utils.orientation(r), 128);
					
					label++;
				}
			}
		}
	}

	private ArrayList<Node> floodFill(int u, int v, ImageProcessor ip, ByteProcessor expanded, int label) {
		Stack<Node> s = new Stack<Node>();
		s.push(new Node(u, v));
		ArrayList<Node> pixels = new ArrayList<Node>();
		
		while(!s.isEmpty()) {

			Node n = s.pop();
			
			ip.putPixel(n.x, n.y, label);
			pixels.add(n);
			expanded.putPixel(n.x, n.y, visited_val);
			if((expanded.getPixel(n.x+1,   n.y) == unvisited_val)) s.push(new Node(n,  1,  0));
			if((expanded.getPixel(  n.x, n.y+1) == unvisited_val)) s.push(new Node(n,  0,  1));
			if((expanded.getPixel(n.x-1,   n.y) == unvisited_val)) s.push(new Node(n, -1,  0));
			if((expanded.getPixel(  n.x, n.y-1) == unvisited_val)) s.push(new Node(n,  0, -1));
		}
		
		return pixels;
	}

}



