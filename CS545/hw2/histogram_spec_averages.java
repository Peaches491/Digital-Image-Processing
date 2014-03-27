import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * CS 545 - Digital Image Processing
 * Homework 2
 * Daniel Miller
 * 
 * Problem 1 - Histogram Spec Averages
 * 
 * 
 * This PlugInFilter processes 8 Bit Greyscale Images. 
 * 
 * This PlugInFilter is designed to match an image's 
 * histogram to an average calculated from a series
 * of sample images. This histogram is specified by 
 * the array 'new_h', and must be calculated by hand.
 * 
 * The filter iterates through the given image's 
 * cumulative histogram, and matches each intensity 
 * to the target histogram. THe algorithm used is
 * specified in Burger & Burge's Algorithm 5.3. 
 * 
 */

public class histogram_spec_averages implements PlugInFilter{
	
	// Histogram to match against
	int[] new_h = new int[]{147,493,364,388,494,657,805,758,769,749,719,660,692,700,720,809,808,898,910,867,956,933,1167,1386,1572,1591,1489,1460,1318,1378,1363,1190,956,847,736,719,642,534,619,633,714,851,803,714,831,749,742,788,863,945,972,921,905,946,1196,1420,1159,1053,919,955,988,1090,1248,1428,1480,1429,1347,1353,1339,1465,1575,1568,1634,1718,1721,1585,1543,1381,1320,1266,1253,1216,1113,1014,1004,993,927,928,882,786,794,655,594,553,507,407,436,447,489,487,499,501,513,490,449,430,408,392,370,363,303,358,334,325,285,330,328,317,354,332,326,321,299,338,318,293,311,313,288,308,291,326,293,284,277,289,317,264,314,346,321,310,342,352,431,460,493,548,524,695,718,637,667,638,698,708,637,665,610,634,685,752,871,772,702,702,687,612,614,659,663,627,628,627,726,695,709,760,793,799,848,833,899,902,876,907,912,920,948,959,1146,1043,1196,1376,1439,1319,1279,1528,2043,1893,1625,1341,1333,1208,1168,1577,2064,2020,2035,2119,2267,2400,3005,2743,2427,2360,2271,1942,1762,2028,2509,2439,2405,2016,2040,2718,2664,3657,2890,2631,2316,1484,815,774,1115,944,1071,1213,1067,994,1085,1093,1675,1471,1160,1145,1033,891,1096,1645,2090,1872,2186,3173,2954,579};
	
	
	// Simple function to create a cumulative histogram given
	//   a regular histogram
	int[] makeCumulative(int[] h){
		int[] result = new int[h.length];
		
		int sum = 0;
		for(int i = 0; i < h.length; i++){
			sum += h[i];
			result[i] = sum;
		}
		
		return result;
	}
	
	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		
		// Declare constants used in algorithm
		int K = 256;
		
		// Calculate needed Cumulative Histograms
		int[] PA = makeCumulative(ip.getHistogram());
		int[] PR = makeCumulative(new_h);
		
		// Make space for the new lookup table
		int[] f = new int[K];
		
		// Populate the lookup table with Histogram matching
		for(int a = 0; a < K; a++){
			int j = K-1;
			do{
				f[a] = j;
				j--;
			} while(j >= 0 && (PA[a] <= PR[j]));
		}
		
		// Apply the lookup table
		ip.applyTable(f);
		
	}

}
