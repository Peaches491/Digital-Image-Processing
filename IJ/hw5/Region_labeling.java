import java.util.Stack;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;


public class Region_labeling implements PlugInFilter {

	private int visited_val = 255;
	private int unvisited_val = 128;
	
	private int unlabeled_val = 1;
	private int label = 2;

	@Override
	public int setup(String arg, ImagePlus imp) {
		return DOES_8G;
	}

	@Override
	public void run(ImageProcessor ip) {
		int w = ip.getWidth();
		int h = ip.getHeight();
		
		int threshold = 255;
				
		ByteProcessor expanded = new ByteProcessor(ip.getWidth(), ip.getHeight());
		for(int u = 0; u<w; u++){
			for(int v = 0; v<h; v++){
				if(ip.getPixel(u, v) != 0){
					ip.putPixel(u, v, unlabeled_val);
					expanded.putPixel( u, v, unvisited_val );
				}
				
			}
		}

		for(int v = 0; v<h; v++){
			for(int u = 0; u<w; u++){
//				int u = 375;
//				int v = 375;
		
				if(ip.getPixelValue(u, v) == unlabeled_val){
					floodFill(u, v, ip, expanded, label);
					label++;
				}
			}
		}
		
	}

	private void floodFill(int u, int v, ImageProcessor ip, ByteProcessor expanded, int label) {
		Stack<Node> s = new Stack<Node>();
		s.push(new Node(u, v));
		
		
		
//		while(!s.isEmpty() && s.size() < 25) {
		while(!s.isEmpty()) {

			Node n = s.pop();
			
//			System.out.print("Popped " + n + ": ");
//			
//			for(Node node : s){
//				System.out.print(node);
//				System.out.print(" ");
//			}
//			System.out.println("");
			
			ip.putPixel(n.x, n.y, label);
			expanded.putPixel(n.x, n.y, visited_val);
			if((expanded.getPixel(n.x+1,   n.y) == unvisited_val)) s.push(new Node(n,  1,  0));
			if((expanded.getPixel(  n.x, n.y+1) == unvisited_val)) s.push(new Node(n,  0,  1));
			if((expanded.getPixel(n.x-1,   n.y) == unvisited_val)) s.push(new Node(n, -1,  0));
			if((expanded.getPixel(  n.x, n.y-1) == unvisited_val)) s.push(new Node(n,  0, -1));
		}
		
	}

}

class Node {
	int x = 0;
	int y = 0;
	Node(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Node(Node n, int i, int j) {
		this.x = n.x + i;
		this.y = n.y + j;
	}
	
	@Override 
	public String toString(){
		return "(" + x + ", " + y + ")";
	}
}

