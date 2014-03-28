import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
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

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		FloatProcessor fp = Utils.ipToFloat(ip);
		
		Utils.laplacian(fp, 8.0);
		
		Utils.displayFloat(ip, fp);
	}
	
}
