import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class Gradient_Magnitude implements PlugInFilter {
	
	private final float[] dfilt = {0.453014f, 0.0f, -0.453014f};
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		applySeperableKernel(ip, dfilt);
	}
	
	
	// Convolves a separable, 1D Kernel to an image in both directions (Horizontal and Vertical) 
	void applySeperableKernel(ImageProcessor ip, float[] h){
		applySeperableKernel_Vert(ip, h);
		applySeperableKernel_Horiz(ip, h);
	}

	// Convolves a separable, 1D Kernel to an image vertically 
	void applySeperableKernel_Vert(ImageProcessor ip, float[] h){
		ip.convolve(h, 1, h.length);
	}

	// Convolves a separable, 1D Kernel to an image horizontally 
	void applySeperableKernel_Horiz(ImageProcessor ip, float[] h){
		ip.convolve(h, h.length, 1);
	}
}
