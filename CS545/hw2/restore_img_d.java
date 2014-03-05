import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 2
 * Daniel Miller
 * 
 * Problem 3 - Restoration of Degraded_D.jpg
 * 
 * 
 * This PlugInFilter processes RGB Images. 
 * 
 * Degraded image A suffers from what appears to be
 * a simple radial or Gaussian blur. As shown in 
 * problem 2 of this assignment, the easiest way to
 * combat this blurring is with a Laplacian Filter.
 * 
 * By reusing my code from problem 2, I was able to 
 * try several values of C. I found that a value of 
 * -14 worked well to recover some finer details of 
 * the source image, and avoided adding more noise
 * and artifacts. At values lower than this, grid-like
 * artifacts begin to appear, possibly due to the 
 * JPG compression. Values of C higher than -14 are
 * still clearer than the original, but there is still
 * room for improvement.
 * 
 */

public class restore_img_d implements PlugInFilter{
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		float c = -14.0f;
		
		float[] h = new float[]{  0.0f,  c/4.0f,    0.0f, 
								c/4.0f,  1.0f-c,  c/4.0f, 
								  0.0f,  c/4.0f,    0.0f};
		
		ip.convolve(h, 3, 3);
	}
}
