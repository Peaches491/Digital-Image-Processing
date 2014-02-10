import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


/**
 * CS 545 - Digital Image Processing
 * Homework 1
 * Daniel Miller
 * 
 * Problem 1 - Quantile based Auto Contrast. 
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 *  
 * Automatically adjusts image's contrast levels, by linarly mapping 
 * the current pixel values to a new scale, which pushes the top and
 * bottom 1% of pixels to saturation. 
 * 
 */
public class AutoContrast_quantiles implements PlugInFilter{
	
	double s_high = 0.01;
	double s_low = 0.01;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		// This function will only operate on 8 bit Greyscale images
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		int[] h = ip.getHistogram();

		
		int pixel_count = ip.getWidth()*ip.getHeight();  // Get number of pixels in image
		int index_low = (int)(s_low * pixel_count);		 // Calculate the lower quantile index
		int index_high = (int)(s_high * pixel_count);	 // Calculate the upper quantile index
		
		// Initialize the lower and upper quantile intensity values
		int i_low = 0; 
		int i_high = 255;
		
		int sum = 0;
		// Start from the low end of the histogram and iterate up. 
		for(int x = 0; x < h.length; x++){
			sum += h[x];
			if(sum >= index_low){ // If we have counted "index_low" number of pixels 
				i_low = x; // Set the minimum intensity for the lower quantile
				break;
			}
		}
		
		IJ.showMessage("Lower quantile threshold: " + i_low);
		
		
		sum = 0;
		// Start from the high end of the histogram and iterate down. 
		for(int x = h.length-1; x >= 0; x--){
			sum += h[x];
			if(sum <= index_high){ // If we have counted "index_low" number of pixels 
				i_high = x; // Set the maximum intensity for the upper quantile
				break;
			}
		}
		
		IJ.showMessage("Upper quantile threshold: " + i_high);
		
		
		
		// For each pixel in the image
		for(int x = 0; x < ip.getWidth(); x++){
			for(int y = 0; y < ip.getHeight(); y++){
				
				int i = ip.get(x, y); // Get the pixel intensity
				
				if(i <= i_low){          // if this pixel falls in the lower quantile
					ip.set(x, y, 0);     // set it's intensity to 0
				} else if(i >= i_high) { // if this pixel falls in the upper quantile
					ip.set(x, y, 255);   // set it's intensity to 255
				} else {				   // Otherwise
										   // Set the intensity based on a linear interpolation
										   // from (i_low, i_high) to (0, 255) 
					ip.set(x, y, (int)((i - i_low) * ((255.0)/(i_high - i_low))) ); 
				}
			}
		}
		
		
	}
	
}
