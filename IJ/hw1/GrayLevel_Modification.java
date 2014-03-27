

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 1
 * Daniel Miller
 * 
 * Problem 1 - Grey Level Modification. 
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 * 
 * Performs point operations across the whole image according to 
 * the following equation: 
 * 
 *             I'(u, v) = 16 * sqrt(I(u, v))
 *             
 *     where:  I(u, v) is the intensity value at pixel (u, v)
 *            I'(u, v) is the new intensity value for the same pixel 
 *
 */
public class GrayLevel_Modification implements PlugInFilter{

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
				
				// set the Intensity to I' = 16 * sqrt(I)
				ip.set(x, y, (int) (16*Math.sqrt(ip.get(x, y))));
			}
		}
	}
	
}
