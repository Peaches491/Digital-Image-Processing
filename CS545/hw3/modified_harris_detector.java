import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 3
 * Daniel Miller
 * 
 * Problem 1 - Automatic Harris Corner Detection
 * 
 * This PlugInFilter applies the book's HarrisCornerDetector methods
 * in order to detect the corners in this image. In an effort to 
 * find the optimal threshold value, this filter will iterate through 
 * several several threshold values, and return the statistics for 
 * value which returns the best corner qualities. 
 * 
 * In order to determine which threshold produces the best corners, 
 * this filter sums the Q value for each corner, and returns the threshold
 * value with the highest Q Sum. 
 * 
 * 
 * 
 * This file depends on the following java files:
 *   HarrisCornerDetector.java
 *   Corner.java
 * 
 */

public class modified_harris_detector implements PlugInFilter{
	
	List<sample> samples = new ArrayList<sample>(1000);
	
	public static class sample implements Comparable<sample>{
		public sample(double Qavg, int t){
			this.Qavg = Qavg;
			this.threshold = t;
		}
		public double Qavg = 0.0;
		public int threshold = 0;
		@Override
		public int compareTo(sample o) {
			return (int) (roundAway(Qavg - o.Qavg));
		}
		public double roundAway(double val) {
		    if (val < 0) {
		        return Math.floor(val);
		    }
		    return Math.ceil(val);
		}
		
	}

	
	@Override
	public int setup(String arg, ImagePlus imp) {
		// Allow this filter to operate on both color and black and white images
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		int start_thresh = 50000;
		int stop_thresh = 500000;
		int t_step = 10000;
		
		
		for(int t = start_thresh; t <= stop_thresh; t += t_step){
			samples.add(testPValue(ip, t));
			IJ.showProgress(stop_thresh-start_thresh, t-start_thresh);
		}
		Collections.sort(samples);
		showCorners(ip, samples.get(samples.size()-1).threshold);
	}

	void showCorners(ImageProcessor ip, int thresh){
		HarrisCornerDetector.Parameters p = new HarrisCornerDetector.Parameters();
		p.threshold = thresh;
		
		HarrisCornerDetector hcd = new HarrisCornerDetector(ip, p);
		List<Corner> corners = hcd.findCorners();
		ImageProcessor result = hcd.showCornerPoints(ip, 1000000);
		ImagePlus win = new ImagePlus("Corners", result);
		win.show();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		IJ.showMessage(" Final threshold: " + thresh + 
				     "\n Number of Corners: " + corners.size() +
				     "\n Q sum: " + averageQ(p, corners));
	}

	
	sample testPValue(ImageProcessor ip, int thresh){
		HarrisCornerDetector.Parameters p = new HarrisCornerDetector.Parameters();
		p.threshold = thresh;
		HarrisCornerDetector hcd = new HarrisCornerDetector(ip, p);
		List<Corner> corners = hcd.findCorners();
		double avg = averageQ(p, corners);
		return new sample(avg, thresh);
	}
	
	double averageQ (HarrisCornerDetector.Parameters p, List<Corner> corners){
		double sum = 0;
		for (Corner c : corners){
			sum += c.getQ() * c.getQ();
		}
//		double Qavg = sum / corners.size();
		double Qavg = sum;
		return Qavg;
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

