import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import imagingbook.lib.math.Complex;


public class DFT_ implements PlugInFilter {

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		FloatProcessor g = (FloatProcessor)ip.duplicate().convertToFloat();
		FloatProcessor Gfp = new FloatProcessor(w, h);
		FloatProcessor Gfp_rev = new FloatProcessor(w, h);
		Complex[][] G = new Complex[h][w];
		
		for(int x = 0; x < w; x++){
			Complex[] c = DFT(getColumn(g, x), true);
			for(int y = 0; y < h; y++){
				G[y][x] = c[y];
			}
		}
		
		for(int y = 0; y < h; y++){
			Complex[] c = DFT(G[y], true);
			float[] f = complexToFloatVec(c);
			for(int x = 0; x < w; x++){
				Gfp.putPixelValue((x+w/2)%w, (y+h/2)%h, Math.sqrt(f[x]));
			}
		}
		
		ImagePlus win = new ImagePlus("DFT", Gfp);
		win.show();

		for(int x = 0; x < w; x++){
			Complex[] c = DFT(getColumn(G, x), false);
			for(int y = 0; y < h; y++){
				G[y][x] = c[y];
			}
		}
		
		for(int y = 0; y < h; y++){
			Complex[] c = DFT(G[y], false);
			float[] f = complexToFloatVec(c);
			for(int x = 0; x < w; x++){
				Gfp_rev.putPixelValue( x, y, f[x] );
			}
		}
		
		
	}

	private float[] complexToFloatVec(Complex[] c) {
		float[] f = new float[c.length];
		for(int i = 0; i < c.length; i++){
			f[i] = (float) c[i].abs();
		}
		return f;
	}
	
	private Complex[] getRow(FloatProcessor fp, int y) {
		Complex[] val = new Complex[fp.getWidth()];
		for(int x = 0; x < fp.getHeight(); x++){
			val[x] = new Complex(fp.getPixel(x, y), 0);
		}
		
		return val;
	}

	private Complex[] getColumn(FloatProcessor fp, int x) {
		Complex[] val = new Complex[fp.getHeight()];
		for(int y = 0; y < fp.getHeight(); y++){
			val[y] = new Complex(fp.getPixel(x, y), 0);
		}
		
		return val;
	}
	
	private Complex[] getColumn(Complex[][] G, int x) {
		Complex[] val = new Complex[G[0].length];
		for(int y = 0; y < G.length; y++){
			val[y] = G[y][x];
		}
		return val;
	}

	Complex[] DFT(Complex[] g, boolean forward){
		int M = g.length;
		double s = 1/ Math.sqrt(M);
		Complex[] G = new Complex[M]; 
		
		for(int m = 0; m < M; m++){
			double sumRe = 0;
			double sumIm = 0;
			double phim = 2* Math.PI * m/M;
			
			for(int u = 0; u < M; u++){
				double gRe = g[u].re;
				double gIm = g[u].im;
				double cosw = Math.cos(phim * u);
				double sinw = Math.sin(phim * u);
				if(!forward) sinw *= -1;
				sumRe += gRe * cosw + gIm * sinw;
				sumIm += gIm * cosw - gRe * sinw;
			}
			G[m] = new Complex(s * sumRe, s * sumIm);
		}
		return G;
	}
}

