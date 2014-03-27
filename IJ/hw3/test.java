import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test{
	
	static List<sample> samples = new ArrayList<sample>(1000);
	
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

	public static void main(String [] args) {

		int start_thresh = 10000;
		int stop_thresh = 500000;
		int t_step = 10000;
		
		
		for(int t = start_thresh; t <= stop_thresh; t += t_step){
			samples.add(new sample(Math.random() * 1000.0f, t));
		}
		
		samples.add(new sample(0.001, 0));
		samples.add(new sample(-0.001, 0));
		samples.add(new sample(0.000, 0));
		
//		Collections.reverse(samples);
		Collections.sort(samples);
		
		for(sample s : samples){
			System.out.println("" + s.Qavg + ", " + s.threshold);
		}
		
		System.out.println("Best: " + samples.get(samples.size()-1).Qavg);
	}

	
	double averageQ (HarrisCornerDetector.Parameters p, List<Corner> corners){
		double sum = 0;
		for (Corner c : corners){
			sum += c.getQ();
		}
//		double Qavg = sum / corners.size();
		double Qavg = sum;
		return Qavg;
	}

}

