import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 1
 * Daniel Miller
 * 
 * Problem 3 - Power Transformation
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 * 
 * Performs point operations across the whole image according to 
 * the following equation: 
 * 
 *             I'(u, v) = c * pow(I(u, v), gamma)
 *             
 *     where:  I(u, v) is the intensity value at pixel (u, v)
 *            I'(u, v) is the new intensity value for the same pixel 
 *                   c is the scaling constant (usually 1)
 *               gamma is the power of the transformation
 *
 */
public class Power_Transform implements PlugInFilter{
	
	double c = 1;
	double gamma = 1.2;

	@Override
	public int setup(String arg, ImagePlus imp) {
		// This function will only operate on 8 bit Greyscale images
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {

		// For each pixel in the image
		for(int x = 0; x < ip.getWidth(); x++){
			for(int y = 0; y < ip.getHeight(); y++){
				
				// set the Intensity to I' = c * pow(I, gamma)
				ip.set(x, y, Math.min( (int) (c*Math.pow(ip.get(x, y), gamma)), 255));
				
				// This version creates weird artifacts caused by rollover from the 8 Bit int. 
				// ip.set(x, y, (int) (c*Math.pow(ip.get(x, y), gamma)) );
			}
		}
	}
	
}
