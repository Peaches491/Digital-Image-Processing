import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 3
 * Daniel Miller
 * 
 * Problem 2d - Zero Crossings
 * 
 * This PlugInFilter applies the three Plugins described previously, 
 * and outputs the result to the screen. 
 * 
 */

public class Laplacian_Edge_Detection implements PlugInFilter {

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		Gradient_Magnitude g = new Gradient_Magnitude();
		Laplacian_ l = new Laplacian_();
		Zero_Crossings z = new Zero_Crossings();
		
		ImageProcessor lap_ip = ip.duplicate();
		
		g.run(ip);
		ip.threshold(10);
		
		l.run(lap_ip);
		z.run(lap_ip);

		ip.copyBits(lap_ip, 0, 0, Blitter.ADD);
	}

}
