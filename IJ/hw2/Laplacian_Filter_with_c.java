import java.awt.TextField;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 2
 * Daniel Miller
 * 
 * Problem 2 - Laplacian Filter
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale images ONLY. 
 * 
 * Performs a Laplacian kernel convolution using the kernel specified in 
 * the assignment. The 'C' value is input into a text box, and the image 
 * updated accordingly. In order to exit the filter, simply enter the same 
 * number twice. I.E, the filter will continue to prompt for new 'C' values,
 * until the same number is entered twice in a row. 
 * 
 * Using the included 'man-8g.jpg' file, I was able to greatly improve
 * the image using a 'C' value of -4.0. With this value, the subject's eyes 
 * become clearer, and the frames of his glasses become sharper. 
 * 
 * At values above -4, additional salt & pepper noise begins to appear on
 * his shoulder.  However, at values below -4, including positive numbers, 
 * the original blurriness in the image is not rectified. 
 * 
 */

public class Laplacian_Filter_with_c implements PlugInFilter{
	
	private ImagePlus imp;
	private boolean running = true;

	@Override
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp; // Store the ImagePlus object for later use
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		float c = 1.0f; // The initial value of C
		
		// Construct a dialog box to prompt the user for values of C
		GenericDialog d = new GenericDialog("Test");
		d.addNumericField("C Value: ", 1.0, 4);
		
		// While the loop has not terminated
		while(running){
			// Prompt the user for a new C value
			d.showDialog();
			
			float old_c = c;
			float new_c = 0.0f;
			
			try{
				new_c = Float.valueOf(((TextField)d.getNumericFields().get(0)).getText());
			} catch(NumberFormatException exp){
				IJ.showMessage("Format Exception", exp.getMessage());
			}
			
			// If this new C is the same as the old C
			if(new_c == old_c) {
				// Stop the loop and end program
				IJ.showMessage("Ending!");
				running = false;
				d.setVisible(false);
				return;
			}
			
			
			// Otherwise, reset the image, and apply new Kernel
			c = new_c;
			ip.reset();
			
			float[] h = new float[]{  0.0f,  c/4.0f,    0.0f, 
									c/4.0f,  1.0f-c,  c/4.0f, 
									  0.0f,  c/4.0f,    0.0f};
			
			// Convolve the image with the above Kernel
			// Convolve method confirmed to use "extend" edge method
			ip.convolve(h, 3, 3);
			
			// Update the displayed image and display to the user
			imp.updateAndDraw();
		}
	}
}
