
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;



public class Chain_codes implements PlugInFilter {

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor orig_ip) {
		ImageProcessor ip = orig_ip.duplicate();
		
		int w = ip.getWidth();
		int h = ip.getHeight();

		ByteProcessor frontier = new ByteProcessor(ip.getWidth(), ip.getHeight());
		
		
		int fg = 255;
		int bg = 0;
		
		Node first_cell = null;
		
		for(int v = 0; v<h; v++){
			for(int u = 0; u<w; u++){
				if(frontierPixel(ip, u, v, fg, bg)){
					frontier.putPixel(u, v, 128);
					if(first_cell == null) first_cell = new Node(u, v);
				}
//				prev_pixel_val = ip.getPixel(u, v); 
			}
		}
		
		
		System.out.println("First Frontier Cell: " + first_cell);
		
		if(first_cell != null) {
			Node previous_cell = null;
			Node current_cell = first_cell;
			int loop = 0;
			do{
				previous_cell = current_cell;
				int code = getChainCode_Single(frontier, current_cell, previous_cell);
//				System.out.println("Node: " + current_cell + " Code: " + code + " Prev: " + previous_cell);
				current_cell = followChain(current_cell, code);
				loop++;
//			} while (current_cell != first_cell && loop < 50);
			} while (loop < 50);
			
//			int code = getChainCode_Single(frontier, current_cell);
//			System.out.println("Code: " + code);
//			current_cell = followChain(current_cell, code);
//			System.out.println(current_cell);
//			
//			code = getChainCode_Single(frontier, current_cell);
//			System.out.println("Code: " + code);
//			current_cell = followChain(current_cell, code);
//			System.out.println(current_cell);
//			
//			code = getChainCode_Single(frontier, current_cell);
//			System.out.println("Code: " + code);
//			current_cell = followChain(current_cell, code);
//			System.out.println(current_cell);
		}
		
//		ImagePlus win = new ImagePlus("Frontier", frontier);
//		win.show();
	}

	

	private int getChainCode_Single(ImageProcessor frontier, Node cell, Node prev) {
		for(int i = 0; i < 8; i++){
			double val = makeChain(frontier, cell, i);
			if(val != 0) {
				System.out.println(followChain(cell, i) + " : " + i + " : " + prev);
				if(followChain(cell, i) != prev) {
					return i;
				} else {
					System.out.println("    " + cell + " != " + prev);
				}
			} 
		}
		return -1;
	}

	private double makeChain(ImageProcessor frontier, Node first_cell, int i) {
		switch (i) {
		case 0:
			return frontier.getPixelValue(first_cell.x + 1, first_cell.y);
		case 1:
			return frontier.getPixelValue(first_cell.x + 1, first_cell.y + 1);
		case 2:
			return frontier.getPixelValue(first_cell.x,     first_cell.y + 1);
		case 3:
			return frontier.getPixelValue(first_cell.x - 1, first_cell.y + 1);
		case 4:
			return frontier.getPixelValue(first_cell.x - 1, first_cell.y);
		case 5:
			return frontier.getPixelValue(first_cell.x - 1, first_cell.y - 1);
		case 6:
			return frontier.getPixelValue(first_cell.x,     first_cell.y - 1);
		case 7:
			return frontier.getPixelValue(first_cell.x + 1, first_cell.y - 1);
		default:
			break;
		}
		return -1;
	}
	
	private Node followChain(Node current_cell, int i) {
		switch (i) {
		case 0:
			return new Node(current_cell,  1,  0);
		case 1:
			return new Node(current_cell,  1,  1);
		case 2:
			return new Node(current_cell,  0,  1);
		case 3:
			return new Node(current_cell, -1,  1);
		case 4:
			return new Node(current_cell, -1,  0);
		case 5:
			return new Node(current_cell, -1, -1);
		case 6:
			return new Node(current_cell,  0, -1);
		case 7:
			return new Node(current_cell,  1, -1);
		default:
			break;
		}
		return null;
	}

	private boolean frontierPixel(ImageProcessor ip, int u, int v, double fg, double bg) {
		if( (ip.getPixel(  u,   v) != fg) ) return false;
		if( (ip.getPixel(u+1,   v) == bg) ) return true;
		if( (ip.getPixel(u-1,   v) == bg) ) return true;
		if( (ip.getPixel(  u, v+1) == bg) ) return true;
		if( (ip.getPixel(  u, v-1) == bg) ) return true;
		
		return false;
	}
	

}
