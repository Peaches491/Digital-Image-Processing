
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
 * Problem 2d - Laplacian Edge Detection
 * 
 * This PlugInFilter applies the image transformations defined in
 * the previous stages of problem 2. The sigma and threshold values 
 * may be changed to suit the user's needs. 
 * 
 * The resulting edges are shown in a new window for comparison
 * against the source images
 * 
 * This file depends on the following java files:
 *   Utils.java
 * 
 */

public class Laplacian_Edge_Detection implements PlugInFilter {

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		double sigma = 1.0;
		double threshold = 10.0;
		
		FloatProcessor fp = Utils.ipToFloat(ip);
		
		FloatProcessor grad = (FloatProcessor) fp.duplicate();
		Utils.gradientMagnitude(grad, sigma);
		Utils.threshold(grad, threshold);
		
		FloatProcessor lap = (FloatProcessor) fp.duplicate();
		Utils.laplacian(lap, sigma);
		ByteProcessor zeros = Utils.zeroCrossings(lap);
		
		ByteProcessor result = new ByteProcessor(grad, true);
		result.copyBits(zeros, 0, 0, Blitter.AND);
		
		Utils.showInWindow(result, "Final result - S: " + sigma + " T: " + threshold);
	}

}
