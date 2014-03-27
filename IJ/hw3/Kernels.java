
public class Kernels {
	// Creates 1D Gaussian Kernels for a given Sigma
	public static float[] makeGaussKernel1D(double sigma, boolean normalize) {
		int center = (int) (3.0 * sigma);
		float[] kernel = new float[2 * center + 1];

		double sigma2 = sigma * sigma;
		double sum = 0.0;

		// Populate the Kernel
		for (int i = 0; i < kernel.length; i++) {
			double r = center - i;
			kernel[i] = (float) Math.exp(-0.5 * (r * r) / sigma2);
			sum += kernel[i];
		}

		// Normalizes the kernel
		if (normalize) {
			for (int i = 0; i < kernel.length; i++) {
				kernel[i] = (float) (kernel[i] / sum);
			}
		}
		return kernel;
	}

	// Creates 1D Gaussian Kernels for a given Sigma
	public static float[] makeGaussKernel1D_secondDerivative(double sigma, boolean normalize) {
		int center = (int) (3.0 * sigma);
		float[] kernel = new float[2 * center + 1];

		double sigma2 = sigma * sigma;
		double sum = 0.0;

		// Populate the Kernel
		for (int i = 0; i < kernel.length; i++) {
			double r = center - i;
			kernel[i] = (float) (Math.exp(-0.5 * (r * r) / sigma2) * 
					((r*r)/(sigma2*sigma2) - 1/sigma2) );
			
			sum += kernel[i];
		}

		// Normalizes the kernel
		if (normalize) {
			for (int i = 0; i < kernel.length; i++) {
				kernel[i] = (float) (kernel[i] / sum);
			}
		}
		return kernel;
	}

	public static float[] derive(float[] H) {

		float[] H_pad = new float[H.length + 2];
		H_pad[0] = 0.0f;
		for (int i = 1; i <= H.length; i++)
			H_pad[i] = H[i - 1];
		H_pad[H_pad.length - 1] = 0.0f;

		float[] new_h = new float[H_pad.length - 2];

		int loop = (int) Math.ceil(new_h.length / 2.0);
		for (int i = 0; i < loop - 1; i++) {
			new_h[i] = H_pad[i + 1] - H_pad[i];
		}

		for (int i = new_h.length - 1; i > new_h.length - loop - 1; i--) {
			new_h[i] = H_pad[i + 2] - H_pad[i + 1];
		}

		return new_h;
	}

	public static void main(String[] args) {
		System.out.println("Gaussian Kernel:");
		float[] H = makeGaussKernel1D(1.0, false);
		for (int i = 0; i < H.length; i++) {
			System.out.print(H[i] + ", ");
		}

		System.out.println("");

		System.out.println("First Derivative:");
		H = (derive(H));
		for (int i = 0; i < H.length; i++) {
			System.out.print(H[i] + ", ");
		}

		System.out.println("");

		System.out.println("Second Derivative:");
		H = makeGaussKernel1D_secondDerivative(1.0, false);
		for (int i = 0; i < H.length; i++) {
			System.out.print(H[i] + ", ");
		}

	}

}
