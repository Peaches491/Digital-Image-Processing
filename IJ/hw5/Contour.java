
import ij.IJ;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Contour implements Comparable<Contour> {
	
	static int INITIAL_SIZE = 50;
	
	private int label;
	private List<Pair<Point, Integer> > points;
	
	public Contour (int label) {
		this.label = label;
		points = new ArrayList<Pair<Point, Integer> >(INITIAL_SIZE);
	}
	
	public void addPoint (Point p, Integer d) {
		points.add(new Pair<Point, Integer>(p, d));
	}
	
	//--------------------- drawing ------------	
		
	Path2D makePolygon() {
		return makePolygon(0.0, 0.0);
	}
	
	public Path2D makePolygon(double xOffset, double yOffset) {
		Path2D path = new Path2D.Float();
		if (points!=null || points.size()>0) {
			Point[] pnts = points.toArray(new Point[0]);
			if (pnts.length > 1){
				path.moveTo(pnts[0].x + xOffset, pnts[0].y + yOffset);
				for (int i=1; i<pnts.length; i++) {
					path.lineTo(pnts[i].x + xOffset,  pnts[i].y + yOffset);
				}
				path.closePath();
			}
			else {	// mark single pixel region "X"
				double x = pnts[0].x;
				double y = pnts[0].y;
				path.moveTo(x + xOffset - 0.5, y + yOffset - 0.5);
				path.lineTo(x + xOffset + 0.5, y + yOffset + 0.5);
				path.moveTo(x + xOffset - 0.5, y + yOffset + 0.5);
				path.lineTo(x + xOffset + 0.5, y + yOffset - 0.5);
			}
		}
		return path;
	}

	//--------------------- chain code ------------	

	
	/*public byte[] makeChainCode8() {
		int m = points.size();
		if (m>1){
			int[] xPoints = new int[m];
			int[] yPoints = new int[m];
			int k = 0;
			Iterator<Pair<Point, Integer> > itr = points.iterator();
			while (itr.hasNext() && k < m) {
				Point cn = itr.next();
				xPoints[k] = cn.x;
				yPoints[k] = cn.y;
				k = k + 1;
			}
			return null;
		}
		else {	// use circles for isolated pixels
			//Point cn = 
				points.get(0);
			return null;
		}
	}*/
	
	public List<Integer> makeChain(boolean diff){
		if (!diff){
			ArrayList<Integer> chain = new ArrayList<Integer>();
			
			for(Pair<Point, Integer> p : this.points){
				chain.add(p.getSecond());
			}
			return chain;
		} else {
			return makeDifferential();
		}
	}
	
	public List<Integer> makeDifferential(){
		List<Integer> chain = this.makeChain(false);
		ArrayList<Integer> diffChain = new ArrayList<Integer>(chain.size());
		int size = chain.size();
		
		for(int i = 0; i < chain.size(); i++){
			if(i == size-1){
				diffChain.add( ((chain.get(0)   - chain.get(i))+8) % 8 );
			} else {
				diffChain.add( (chain.get(i+1) - chain.get(i)) % 8 );
			}
		}
		
		return diffChain;
	}

	private int getShapeNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	//--------------------- retrieve contour points -------
	
	public List<Point> getPointList() {
		ArrayList<Point> chain = new ArrayList<Point>();
		
		for(Pair<Point, Integer> p : this.points){
			chain.add(p.getFirst());
		}
		
		return chain;
	}
	
	public Point[] getPointArray() {
		return points.toArray(new Point[0]);
	}
		
	//--------------------- contour statistics ------------
	
	public int getLength() {
		return points.size();
	}
	
	public int getLabel() {
		return label;
	}
	
	//--------------------- debug methods ------------------
	
	public String toString(){
		return
			"Contour " + label + ": " + this.getLength() + " points";
	}
	
	//--------------------- compare method for sorting ------------------
	
	// Compare method for sorting ontours by length (longer contours at front)
	public int compareTo(Contour c2) {
		return c2.points.size() - this.points.size();
	}
	
	public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (!(obj instanceof Contour))
            return false;

        Contour rhs = (Contour) obj;
        return this.hashCode() == rhs.hashCode();
    }
	
	public int hashCode() {
		return this.getShapeNumber();
	}

}
