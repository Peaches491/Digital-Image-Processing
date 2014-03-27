import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
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
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		FloatProcessor fp = Utils.ipToFloat(ip);
		
		Utils.gradientMagnitude(fp, 4.0);
		
		Utils.displayFloat(ip, fp);
	}
}
