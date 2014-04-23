
public class Node {
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
	
	@Override
	public boolean equals(Object obj){
		if( obj == null) return false;
		if( !(obj instanceof Node) ) return false;
		Node o = (Node) obj;
		if( this.x != o.x) return false;
		if( this.y != o.y) return false;
		return true;
	}
	
	
	public static void main(String[] args){
		Node n1 = new Node(0, 0);
		Node n2 = new Node(0, 0);
		
		System.out.println(n1.equals(n2));
	}
}
