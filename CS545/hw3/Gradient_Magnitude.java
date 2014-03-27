import ij.ImagePlus;
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
 * Problem 2a - Gradient Magnitude
 * 
 * This PlugInFilter applies a seperable Gradient Magnitude
 * kernel, dfilt, after gaussian-smoothing the given image.  
 * 
 */

public class Gradient_Magnitude implements PlugInFilter {
	
	private final float[] dfilt = {-0.5f, 0.0f, 0.5f};
	private FloatProcessor floatIP;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {

		float[] gfilt = makeGaussKernel1D(1);
		ip.convolve(gfilt, 1, gfilt.length);
		ip.convolve(gfilt, gfilt.length, 1);
		
		if (ip instanceof FloatProcessor) {
			floatIP = (FloatProcessor) ip.duplicate();
		}
		else {
			floatIP = (FloatProcessor) ip.convertToFloat();
		}
		
		floatIP.convolve(dfilt, 1, dfilt.length);
		floatIP.convolve(dfilt, dfilt.length, 1);
		
		
		if (ip instanceof ByteProcessor) {
			ip.setPixels(floatIP.convertToByte(true).getPixels());
		} else {
			ip.setPixels(floatIP.getPixels());
		}
	}
	
	// Creates 1D Gaussian Kernels for a given Sigma 
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
