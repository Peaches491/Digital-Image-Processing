

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

// spine.jpg - When gamma is set to 1.4, a strange collision of the 
//				 5th and 6th vertebrae is exposed

// runway.jpg - Gamma 0.97 helps to reduce the glaring brightness of the
//    			 image, but does not increase contrast enough to combat
//				 the overall "haziness"

public class Power_Transform implements PlugInFilter{
	
	double c = 1;
	double gamma = 1.20;

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
			}
		}
	}
	
}
