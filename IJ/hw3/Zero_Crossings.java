import java.awt.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 3
 * Daniel Miller
 * 
 * Problem 2c - Zero Crossings
 * 
 * This PlugInFilter applies a simple zero-crossing detection, 
 * resulting in a binary image. 
 * 
 */

public class Zero_Crossings implements PlugInFilter {
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_32;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		ByteProcessor result = Utils.zeroCrossings(ip);
		
		ImagePlus win = new ImagePlus("Corners", result);
		win.show();
		
	}
}

