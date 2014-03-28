import ij.ImagePlus;
import ij.plugin.filter.Convolver;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Daniel Miller
 * 
 * Utility Functions
 * 
 */

public class Utils {

	static void applyGaussian(FloatProcessor ip, double sigma) {
		float[] gfilt = Kernels.makeGaussKernel1D(sigma, true);
		
		ip.convolve(gfilt, 1, gfilt.length);
		ip.convolve(gfilt, gfilt.length, 1);
	}
	
	static void gradientMagnitude(FloatProcessor ip, double sigma) {
		float[] gfilt = Kernels.derive(Kernels.makeGaussKernel1D(sigma, true));
		
		gfilt[(int) Math.floor(gfilt.length/2.0)] = 0.0f;
		
		
		ImageProcessor fp_horiz = ip.duplicate();
		ImageProcessor fp_vert = ip.duplicate();
		
		fp_vert.convolve(gfilt, 1, gfilt.length);
		fp_horiz.convolve(gfilt, gfilt.length, 1);
		
		fp_vert.sqr();
		fp_horiz.sqr();
		
		fp_horiz.copyBits(fp_vert,0,0,Blitter.ADD);
		fp_horiz.sqrt();
		
		ip.setPixels(fp_horiz.getPixels());
		
	}
	
	static void laplacian(FloatProcessor ip, double sigma) {
		float[] lfilt = Kernels.makeGaussKernel1D_secondDerivative(sigma, false);
		
//		lfilt = new float[] {1.0f, -2.0f, 1.0f };
		
//		applyGaussian(ip, 4.0);
		
		ImageProcessor vert = ip.duplicate();
		ImageProcessor horiz = ip.duplicate();

		Convolver cv = new Convolver();
		cv.setNormalize(false);
		cv.convolve(vert, lfilt, 1, lfilt.length);
		cv.convolve(horiz, lfilt, lfilt.length, 1);

		vert.copyBits(horiz, 0, 0, Blitter.ADD);
		
		ip.setPixels(vert.getPixels());
	}

	public static FloatProcessor ipToFloat(ImageProcessor ip) {
		FloatProcessor result;
		if (ip instanceof FloatProcessor) {
			result = (FloatProcessor) ip.duplicate();
		}
		else {
			result = (FloatProcessor) ip.convertToFloat();
		}
		return result;
	}

	public static void displayFloat(ImageProcessor ip, FloatProcessor fp) {
		if (ip instanceof ByteProcessor) {
			ip.setPixels(fp.convertToByte(true).getPixels());
		} else if (ip instanceof FloatProcessor) {
			ip.setPixels(fp.convertToFloat().getPixels());
		}
	}

	public static void threshold(FloatProcessor fp, double d) {
		for(int x = 0; x < fp.getWidth(); x++){
			for(int y = 0; y < fp.getHeight(); y++){
				if(fp.getPixelValue(x, y) < d) fp.putPixelValue(x, y, 0);
				else fp.putPixelValue(x, y, 255);
			}
		}
	}

	public static void showInWindow(ImageProcessor ip, String title) {
		ImagePlus win = new ImagePlus(title, ip.duplicate());
		win.show();
	}

	public static ByteProcessor zeroCrossings(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ByteProcessor result = new ByteProcessor(w, h);
		
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				if(ip.getPixelValue(x+1, y) * ip.getPixelValue(x-1, y) < 0.0 ||
				   ip.getPixelValue(x, y+1) * ip.getPixelValue(x, y-1) < 0.0){
					result.putPixel(x, y, 255);
				} else {
					result.putPixel(x, y, 0);
				}
			}
		}
		
		return result;
	}

}
