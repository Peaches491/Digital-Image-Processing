//package hw2;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Laplacian_Filter_with_c implements PlugInFilter{

	
	float c = -1.0f;
	
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		IJ.showMessage("Running... ");
		
		float[] h = new float[]{  0.0f,  c/4.0f,    0.0f, 
								c/4.0f,  1.0f-c,  c/4.0f, 
								  0.0f,  c/4.0f,    0.0f};
		
		ip.convolve(h, 3, 3);
		
		IJ.showMessage("Done!");
		
	}

}
