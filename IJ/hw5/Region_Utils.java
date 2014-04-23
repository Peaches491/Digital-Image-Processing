import java.util.ArrayList;

public class Region_Utils {
	
	public static Centroid centroid(ArrayList<Node> list){
		int sum_x = 0;
		int sum_y = 0;
		
		for(Node n : list){
			sum_x += n.x;
			sum_y += n.y;
		}
		Centroid c = new Centroid(1.0/list.size() * sum_x, 1.0/list.size() * sum_y);
		return c;
	}
	
	
	public static double central_moment(ArrayList<Node> list, int p, int q){
		double sum = 0;
		
		Centroid c = centroid(list);
		
		for(Node n : list){
			sum += Math.pow((n.x - c.x), p) * Math.pow((n.y - c.y), q);
		}
		
		return sum;
	}
	
	public static double orientation(ArrayList<Node> list){
		double val = 0.5 * Math.atan2(2*central_moment(list, 1, 1), central_moment(list, 2, 0) - central_moment(list, 0, 2));
		return val;
	}
	
	public static Eccentricity eccentricity(ArrayList<Node> list){
		double a1 = central_moment(list, 2, 0) + central_moment(list, 0, 2);
		a1 += Math.sqrt(Math.pow(central_moment(list, 2, 0) - central_moment(list, 0, 2), 2) + 4*Math.pow(central_moment(list, 1, 1), 2));
		
		double a2 = central_moment(list, 2, 0) + central_moment(list, 0, 2);
		a2 -= Math.sqrt(Math.pow(central_moment(list, 2, 0) - central_moment(list, 0, 2), 2) + 4*Math.pow(central_moment(list, 1, 1), 2));
		
		return new Eccentricity(a1, a2, list.size());
	}
	
}



class Centroid {
	double x = 0.0;
	double y = 0.0;
	
	public Centroid(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override 
	public String toString(){
		return "Centroid (x: " + x + ", y: " + y + ")";
	}
}

class Eccentricity{
	double a1 = 0.0;
	double a2 = 0.0;
	int region_size = 0;
	
	public Eccentricity(double r1, double r2, int region_size) {
		this.a1 = r1;
		this.a2 = r2;
		this.region_size = region_size; 
	}
	
	public double getEcc(){
		return this.a1/this.a2;
	}
	
	public double getRA(){
		return Math.sqrt(2.0*this.a1/this.region_size);
	}
	
	public double getRB(){
		return Math.sqrt(2.0*this.a2/this.region_size);
	}
	
	
	@Override 
	public String toString(){
		return "Centroid (x: " + a1 + ", y: " + a2 + ")";
	}
}