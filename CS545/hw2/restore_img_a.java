import java.util.ArrayList;
import java.util.Collections;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 2
 * Daniel Miller
 * 
 * Problem 3 - Restoration of Degraded_A.jpg
 * 
 * 
 * This PlugInFilter processes RGB Images. 
 * 
 * Degraded image A suffers from RGB-style "Salt and
 * Pepper" noise. In order to combat this noise, this 
 * plugin implements a 3x3 median filter. This non-linear
 * filter iterates through the given image, and sets each 
 * pixel equal to the median intensity of it's neighbors.
 * 
 * As shown in the included image, 'Restored_A.jpg', this
 * filter removes all of the random points obscuring the
 * original image. However, this filter does lose some 
 * of the finer details. 
 * 
 */

public class restore_img_a implements PlugInFilter{

	int size = 1;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_RGB;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		ImageProcessor copy = ip.duplicate();
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		ArrayList<Integer> P = new ArrayList<Integer>();
		
		for(int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				
				int end = size*2 + 1;
				
				for(int u = 0; u <= end; u++){
					for(int v = 0; v <= end; v++){
						int clamped_u = Math.max(0, Math.min(w-1, i+(u-size)));
						int clamped_v = Math.max(0, Math.min(h-1, j+(v-size)));
						
						P.add(copy.getPixel(clamped_u, clamped_v) );
					}
				}
				Collections.sort(P);
				ip.set(i, j, P.get( (int) Math.pow(size+1, 2) ));
				P.clear();
			}
		}
	}
}


