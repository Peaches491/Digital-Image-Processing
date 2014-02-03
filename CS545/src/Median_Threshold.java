import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 1
 * Daniel Miller
 * 
 * Problem 2 - Median Thresholding
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 * 
 * Performs a threshold operation with the threshold set at the 
 * the median intensity. 
 * 
 */
public class Median_Threshold implements PlugInFilter{
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		// This function will only operate on 8 bit Greyscale images
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {

		int[] h = ip.getHistogram();
		int idx = ip.getWidth()*ip.getHeight()/2;
		int h_th = 0;
		
		int count = 0;
		int bin = 0;
		
		// Loop through the histogram, counting the number of pixels as you go. 
		// Keep track of the bin index as well. 
		while(count < ip.getWidth()*ip.getHeight() && bin < h.length){
			
			// If this bin contains the median pixel
			if(count + h[bin] > idx){
				// Set the threshold value and break
				h_th = bin;
				break;
			}
			
			count += h[bin];
			bin++;
		}
		
		// Display threshold to user for debugging purposes
		IJ.showMessage("Threshold value: " + h_th);
		
		// Set threshold of image. 
		ip.threshold(h_th);
	}
	
}
