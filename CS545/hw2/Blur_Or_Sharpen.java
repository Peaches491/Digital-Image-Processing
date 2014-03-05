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
 * Problem 4b - Spatial Filtering
 * 
 * 
 * This PlugInFilter processes RGB and Greyscale images. 
 * 
 * This PlugInFilter performs a Spacial Filtering with 
 * a given Sigma and W values. These values are input 
 * into a text box, and the image updated accordingly. 
 * In order to exit the filter, simply enter the same 
 * number two times in a row. 
 * 
 * The resulting image is formed based on the following 
 * equation:
 * 
 *        I' = (1+w)*I - w*I*G
 *        
 *   where: I is the intensity of the image
 *   		G is the intensity of the blurred image
 *      and w is the amount of blurring/sharpening
 *      
 *      
 * w = -1 produces the most blurring. This is because
 *    the above equation will simplify to I' = w*I*G
 *    when w = -1
 *    
 * w = 1 produces the most Sharpening. This is because
 *    the above equation will become 2*I + w*I*G. 
 *    At this point, the filter begins to perform
 *    Unsharp Masking, enhancing the contrast in the
 *    image, and making edges more pronounced. 
 * 
 */

public class Blur_Or_Sharpen implements PlugInFilter{

	boolean running = true;
	private boolean first_run = true;
	
	private ImagePlus imp;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		// Allow this filter to operate on both color and black and white images
		return DOES_RGB | DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		// Store the image's height and width
		int height = ip.getHeight();
		int width = ip.getWidth();
		
		// Create two duplicates. One to store a copy of the original, and one to be blurred
		ImageProcessor orig = ip.duplicate();
		ImageProcessor blur = ip.duplicate();
		
		// Set initial values for W and Sigma
		float w = -0.50f;
		float sigma = 10.0f;
			
		// While the loop has not terminated
		while(running){

			float old_w = w;
			float new_w = 0.0f;
			
			float old_sigma = sigma;
			float new_sigma = 0.0f;
			
			if(first_run ){
				old_w = new_w;
				old_sigma = new_sigma;
				first_run = false;
			}

			// Prompt the user for new W and Sigma values
			GenericDialog d = new GenericDialog("Test");
			d.addNumericField("W Value: ", w, 4);
			d.addNumericField("Sigma Value: ", sigma, 4);
			d.showDialog();

			// Retrieve the new values
			try{
				new_w = Float.valueOf(((TextField)d.getNumericFields().get(0)).getText());
				new_sigma = Float.valueOf(((TextField)d.getNumericFields().get(1)).getText());
			} catch(NumberFormatException exp){
				IJ.showMessage("Format Exception", exp.getMessage());
				return;
			}
			
			w = new_w;
			sigma = new_sigma;
			

			// If the new values are unchanged
			if(new_w == old_w && new_sigma == old_sigma) {
				// Stop the loop and end program
				IJ.showMessage("Ending!");
				running = false;
				d.setVisible(false);
				return;
			}

			// Otherwise, reset the image, and apply new Kernel
			ip.reset();
			blur.reset();
			
			// Construct 1D Gaussian Kernel
			float[] h = this.makeGaussKernel1D(sigma);
			
			// Apply this kernel horizontally and vertically
			blur.convolve(h, 1, h.length);
			blur.convolve(h, h.length, 1);
			
			// For each pixel
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					int I = orig.get(i, j);
					int G = blur.get(i, j);
					float val = (1+w)*I - w*G;
					// Set the intensity to (1+w)I - w*I*G
					val = Math.max(0, Math.min(255, val));
					ip.set(i, j, (int) val);
				}
			}

			// Update the displayed image and display to the user
			imp.updateAndDraw();
		}
	}
	
	
	// Creates 1D Gaussian Kernels for a given Sigma 
	float[] makeGaussKernel1D(double sigma){
		int center = (int) (3.0*sigma);
		float[] kernel = new float[2*center+1];
		
		double sigma2 = sigma*sigma;
		double sum = 0.0;
		
		// Populate the Kernel
		for(int i = 0; i<kernel.length; i++){
			double r = center - i;
			kernel[i] = (float) Math.exp(-0.5 * (r*r) / sigma2);
			sum += kernel[i];
		}
		
		// Normalizes the kernel
		for(int i = 0; i<kernel.length; i++){
			kernel[i] = (float) (kernel[i]/sum);
		}
		return kernel;
	}

}
