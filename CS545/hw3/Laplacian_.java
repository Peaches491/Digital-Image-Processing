import ij.ImagePlus;
import ij.plugin.filter.Convolver;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 3
 * Daniel Miller
 * 
 * Problem 2b - Gradient Magnitude
 * 
 * This PlugInFilter applies a seperable Laplacian 
 * kernel, lfilt, after gaussian-smoothing the given image.  
 * 
 * However, this kernel is not seperable in the typical fashion. 
 * Rather, the final pixel value is calculated as the following:
 *    I' = H_x*I + H_y*I 
 * 
 */

public class Laplacian_ implements PlugInFilter {

	private final float[] lfilt = {1.0f, -2.0f, 1.0f};
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		float[] gfilt = makeGaussKernel1D(4);
		
		Convolver cv = new Convolver();
		cv.setNormalize(false);
		cv.convolve(ip, gfilt, 1, gfilt.length);// first 1-D convolution process for the image J
		cv.convolve(ip, gfilt, gfilt.length, 1);
		
		
		ImageProcessor vert = ip.convertToFloat().duplicate();
		ImageProcessor horiz = vert.duplicate();
		
		cv.convolve(vert, lfilt, 1, lfilt.length);
		cv.convolve(horiz, lfilt, lfilt.length, 1);

		vert.copyBits(horiz, 0, 0, Blitter.ADD);
		
		ip.setPixels(vert.getPixels());
	}
	
	
	
	float[] makeGaussKernel1D(double sigma){
		int center = (int) (3.0*sigma);
		float[] kernel = new float[2*center+1];
		
		double sigma2 = sigma*sigma;
		double sum = 0.0;
		
		// Populate the Kernel
		for(int i = 0; i<kernel.length; i++){
			double r = center - i;
			kernel[i] = (float) Math.exp(-0.5 * (r*r) / sigma2);
			sum += kernel[i];
		}
		
		// Normalizes the kernel
		for(int i = 0; i<kernel.length; i++){
			kernel[i] = (float) (kernel[i]/sum);
		}
		return kernel;
	}
	
	
}
