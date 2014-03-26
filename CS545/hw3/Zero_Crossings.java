import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


public class Zero_Crossings implements PlugInFilter {

	private FloatProcessor floatIP;

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		if (ip instanceof FloatProcessor) {
			floatIP = (FloatProcessor) ip.duplicate();
		}
		else {
			floatIP = (FloatProcessor) ip.convertToFloat();
		}
		
		
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				floatIP.set(x, y, (floatIP.get(x, y)==0 ? 1 : 0));
			}
		}
		
		if (ip instanceof ByteProcessor) {
			ip.setPixels(floatIP.convertToByte(true).getPixels());
		} else {
			ip.setPixels(floatIP.getPixels());
		}
	}

}
