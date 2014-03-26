import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


public class Laplacian_ implements PlugInFilter {

	private final float[] lfilt = {1.0f, -2.0f, 1.0f};
	private FloatProcessor vert;
	private FloatProcessor horiz;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		if (ip instanceof FloatProcessor) {
			vert = (FloatProcessor) ip.duplicate();
			horiz = (FloatProcessor) ip.duplicate();
		}
		else {
			vert = (FloatProcessor) ip.convertToFloat();
			horiz = (FloatProcessor) ip.convertToFloat();
		}
		
		
		vert.convolve(lfilt, 1, lfilt.length);
		horiz.convolve(lfilt, lfilt.length, 1);
		
		vert.copyBits(horiz, 0, 0, Blitter.ADD);
		
		if (ip instanceof ByteProcessor) {
			ip.setPixels(vert.convertToByte(true).getPixels());
		} else if (ip instanceof FloatProcessor) {
			ip.setPixels(vert.getPixels());
		}

	}
}
