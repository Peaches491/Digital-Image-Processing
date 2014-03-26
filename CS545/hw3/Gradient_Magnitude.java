import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


public class Gradient_Magnitude implements PlugInFilter {
	
	private final float[] dfilt = {0.453014f, 0.0f, -0.453014f};
	private FloatProcessor floatIP;
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		if (ip instanceof FloatProcessor) {
			floatIP = (FloatProcessor) ip.duplicate();
		}
		else {
			floatIP = (FloatProcessor) ip.convertToFloat();
		}
		
		floatIP.convolve(dfilt, 1, dfilt.length);
		floatIP.convolve(dfilt, dfilt.length, 1);
		
		
		if (ip instanceof ByteProcessor) {
			ip.setPixels(floatIP.convertToByte(true).getPixels());
		}
	}
	
}
