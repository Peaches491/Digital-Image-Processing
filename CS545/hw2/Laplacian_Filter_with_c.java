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
		this.imp = imp;
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		float c = 1.0f;
		GenericDialog d = new GenericDialog("Test");
		d.addNumericField("C Value: ", 1.0, 4);
		
		while(running){
			d.showDialog();
			
			float old_c = c;
			float new_c = 0.0f;
			
			try{
				new_c = Float.valueOf(((TextField)d.getNumericFields().get(0)).getText());
			} catch(NumberFormatException exp){
				IJ.showMessage("Format Exception", exp.getMessage());
			}
			
			if(new_c == old_c) {
				IJ.showMessage("Ending!");
				running = false;
				d.setVisible(false);
				break;
			}
			
			c = new_c;
			
			ip.reset();
			
			float[] h = new float[]{  0.0f,  c/4.0f,    0.0f, 
									c/4.0f,  1.0f-c,  c/4.0f, 
									  0.0f,  c/4.0f,    0.0f};
			
			ip.convolve(h, 3, 3);
			
			imp.updateAndDraw();
		}
	}
}
