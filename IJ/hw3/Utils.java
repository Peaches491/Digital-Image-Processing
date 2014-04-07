import ij.plugin.filter.Convolver;
import ij.process.Blitter;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class Utils {

	static void applyGaussian(FloatProcessor ip, double sigma) {
		float[] gfilt = Kernels.makeGaussKernel1D(sigma, true);
		
		ip.convolve(gfilt, 1, gfilt.length);
		ip.convolve(gfilt, gfilt.length, 1);
	}
	
	static void gradientMagnitude(FloatProcessor ip, double sigma) {
		float[] gfilt = Kernels.derive(Kernels.makeGaussKernel1D(sigma, true));
		
		gfilt[(int) Math.floor(gfilt.length/2.0)] = 0.0f;
		
		ip.convolve(gfilt, 1, gfilt.length);
		ip.convolve(gfilt, gfilt.length, 1);
	}
	
	static void laplacian(FloatProcessor ip, double sigma) {
		float[] lfilt = Kernels.makeGaussKernel1D_secondDerivative(sigma, false);
		
//		lfilt = new float[] {1.0f, -2.0f, 1.0f };
		
//		applyGaussian(ip, 4.0);
		
		ImageProcessor vert = ip.duplicate();
		ImageProcessor horiz = ip.duplicate();

		Convolver cv = new Convolver();
		cv.setNormalize(false);
		cv.convolve(vert, lfilt, 1, lfilt.length);
		cv.convolve(horiz, lfilt, lfilt.length, 1);

		vert.copyBits(horiz, 0, 0, Blitter.AND);
		
		ip.setPixels(vert.getPixels());
	}

	public static FloatProcessor ipToFloat(ImageProcessor ip) {
		FloatProcessor result;
		if (ip instanceof FloatProcessor) {
			result = (FloatProcessor) ip.duplicate();
		}
		else {
			result = (FloatProcessor) ip.convertToFloat();
		}
		return result;
	}

	public static void displayFloat(ImageProcessor ip, FloatProcessor fp) {
		if (ip instanceof ByteProcessor) {
			ip.setPixels(fp.convertToByte(true).getPixels());
		} else if (ip instanceof FloatProcessor) {
			ip.setPixels(fp.convertToFloat().getPixels());
		}
	}

}
