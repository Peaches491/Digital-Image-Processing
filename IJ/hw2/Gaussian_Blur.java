import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 2
 * Daniel Miller
 * 
 * Problem 4a - Gaussian Blur
 * 
 * 
 * This PlugInFilter processes RGB Images. 
 * 
 * This PlugInFilter implements the makeGaussKernel1D()
 * function shown on page 103 of the text. However,  
 * an additional loop is added which ensures the
 * resulting kernel is normalized. The filter then 
 * calls the ImageProcessor.convolve() method twice,
 * the first time specifying a vertical kernel, and 
 * the second time, horizontal. 
 * 
 * The amount of blurring can be changed by editing the 
 * 'sigma' field.
 *
 */

public class Gaussian_Blur implements PlugInFilter{
	
	double sigma = 5.0;
	
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
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB | DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		// Construct a 1D Gaussian Kernel
		float[] h = this.makeGaussKernel1D(sigma);
		
		// I've confirmed that convolve() will handle edge 
		//   pixels with the "extend' method
		ip.convolve(h, 1, h.length);
		ip.convolve(h, h.length, 1);
	}
}
