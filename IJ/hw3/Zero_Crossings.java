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
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ImageProcessor result = new ByteProcessor(w, h);
		
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				if(ip.getPixelValue(x+1, y) * ip.getPixelValue(x-1, y) < 0.0 ||
				   ip.getPixelValue(x, y+1) * ip.getPixelValue(x, y-1) < 0.0){
					result.putPixel(x, y, 255);
				} else {
					result.putPixel(x, y, 0);
				}
			}
		}
		
		ImagePlus win = new ImagePlus("Corners", result);
		win.show();
		
	}
}

