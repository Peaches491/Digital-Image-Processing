import ij.IJ;
import ij.ImagePlus;
import ij.gui.HistogramWindow;
import ij.plugin.filter.PlugInFilter;
import ij.plugin.frame.PlugInFrame;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 1
 * Daniel Miller
 * 
 * Problem 4 - Non-Linear Binning
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 * 
 * Performs a histogram operation against an array of arbitrarily 
 * sized bins. Bin sizes are specified by the "bins" variable
 * 
 */
public class NonLinear_Binning implements PlugInFilter{


	int[] bins = {13, 15, 25, 25, 50, 50, 25, 25, 15, 13};
	int[] B_min = new int[bins.length];
	int[] B_max = new int[bins.length];
	int[] H = new int[bins.length];
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		
		int sum = 0;
		for(int x = 0; x < bins.length; x++){
			B_min[x] = sum;
			sum += bins[x];
			B_max[x] = sum;
		}
		
		return DOES_8G;
	}

	private int getBin(int val) {
		
		// Loop through the list of possible bins. 
		// Once the pixel intensity fits between a B_min B_max pair, 
		// return that bin number. 
		for(int x = 0; x < bins.length; x++){
			if(B_min[x] <= val && val < B_max[x]) return x;
		}
		
		// If no bins hold this pixel, return -1
		return -1;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		if(bins.length != 1) IJ.showMessage("Creating histogram with " + bins.length + " bins");
		else IJ.showMessage("Creating histogram with " + bins.length + " bin");
		
		// For each pixel in the image
		for(int u = 0; u < ip.getWidth(); u++){
			for(int v = 0; v < ip.getHeight(); v++){

				// Get the pixel's intensity
				int a = ip.getPixel(u, v);
				// Use the getBin() function to select the appropriate bin index
				int i = getBin(a);
				// Increment the appropriate bin if index >= 0
				if(i >= 0) H[i]++;
			}
		}
		
		// Generate and print results string
		String s = "";
		
		for(int x = 0; x < H.length; x++){
			s += "[" + B_min[x] + ", " + B_max[x] + "): " + H[x] + "\n";
		}
		
		IJ.showMessage(s);
		
	}

}
