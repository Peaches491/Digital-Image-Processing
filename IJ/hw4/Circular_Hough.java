import java.util.Arrays;
import java.util.Collections;

import ij.ImagePlus;
import ij.ImageStack;
import ij.plugin.filter.PlugInFilter;
import ij.process.Blitter;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;


public class Circular_Hough implements PlugInFilter {
	
	
	int p_min = 10/2;
	int p_max = 200/2;
	int p_size = p_max - p_min;
	
	double edge_threshold = 80;
	
	int img_w;
	int img_h;

	
	@Override
	public int setup(String arg, ImagePlus imp) {
		this.img_w = imp.getWidth();
		this.img_h = imp.getHeight();
		
		return DOES_ALL;
	}

	@Override
	public void run(ImageProcessor ip) {
		FloatProcessor fp = (FloatProcessor) ip.duplicate().convertToFloat();
		
		Laplacian_Edge_Detection lap = new Laplacian_Edge_Detection();
		
		lap.run(fp);
		
		
		ImageProcessor edges = fp.convertToByte(true);
		edges.threshold(10);
//		new ImagePlus("Edges", edges).show();   	
		
		/////////////////////////////////////////////////////////////
		ImageStack stack = ImageStack.create(img_w, img_h, p_size, 32);
		
		for(int u = 0; u < img_w; u++){
			for(int v = 0; v < img_h; v++){
				if(edges.getPixelValue(u, v) > 1){
					
					for(int p_idx = 1; p_idx <= p_size; p_idx++){
						int r = p_idx + p_min - 1;
						incrementCircle(stack.getProcessor(p_idx), u, v, r);
					}
				}
			}
		}

		
		//////////////////////////////////////////////////////////////
		ImageStack max_supp = ImageStack.create(img_w, img_h, p_size, 32);
		for(int p_idx = 1; p_idx <= p_size; p_idx++){
			max_supp.addSlice("", stack.getProcessor(p_idx));
		}
		
		
		
		for(int p_idx = 1; p_idx <= p_size; p_idx++){
			FloatProcessor p_ip = (FloatProcessor) stack.getProcessor(p_idx);
			int r = p_idx + p_min - 1;
			float level = 0;
			
			for(int u = 0; u < img_w; u++){
				for(int v = 0; v < img_h; v++){
					float i = p_ip.getPixelValue(u, v);
					if(i > level) level = i;  
				}
			}
			
			level = level * 0.9999f;
			if(level < 75) level = 75.0f;
			
			System.out.println("Thresholding to: " + level);
			
			for(int u = 0; u < img_w; u++){
				for(int v = 0; v < img_h; v++){
					float val = p_ip.getPixelValue(u, v);
					if ((val > level) && Utils.isMax(p_ip, u, v)){
						p_ip.putPixelValue(u, v, 1.0f);
						System.out.println("Set pixel at intensity: " + val);
						Utils.drawCorner(ip, u, v, 127);
						Utils.drawCircle(ip, u, v, r, 127);
					} else {
						p_ip.putPixelValue(u, v, 0.0f);
					}
				}
			}
		}
		
		new ImagePlus("Results: Accumulator Array", stack).show();
	}

	
	
	// Bresenham Circles implementation found here: 
	//    http://onyx.boisestate.edu/~tcole/cs498/spr04/ammeraal/Bresenham.java
	private void incrementCircle(ImageProcessor p, int u, int v, int r) {
//		p.setLineWidth(1);
//		p.drawOval(u-r, v-r, r*2, r*2);
		
		int x = 0;
		int y = r;

		double F = -r + 0.25;

		p.putPixelValue(u+x, v+y, p.getPixelValue(u+x, v+y)+1);
		p.putPixelValue(u+y, v+x, p.getPixelValue(u+y, v+x)+1);
		p.putPixelValue(u+x, v-y, p.getPixelValue(u+x, v-y)+1);
		p.putPixelValue(u-y, v+x, p.getPixelValue(u-y, v+x)+1);
		
		while (x < y-1) {

			F = F + 2 * x + 1;
			x = x + 1;

			if (F >= 0) {
				F = F - 2 * y + 2;
				y = y - 1;
			}
			
			p.putPixelValue(u+x, v+y, p.getPixelValue(u+x, v+y)+1);
			p.putPixelValue(u+y, v+x, p.getPixelValue(u+y, v+x)+1);
			p.putPixelValue(u-x, v+y, p.getPixelValue(u-x, v+y)+1);
			p.putPixelValue(u+y, v-x, p.getPixelValue(u+y, v-x)+1);
			
			p.putPixelValue(u+x, v-y, p.getPixelValue(u+x, v-y)+1);
			p.putPixelValue(u-y, v+x, p.getPixelValue(u-y, v+x)+1);
			p.putPixelValue(u-x, v-y, p.getPixelValue(u-x, v-y)+1);
			p.putPixelValue(u-y, v-x, p.getPixelValue(u-y, v-x)+1);
		}
		
	}

}
