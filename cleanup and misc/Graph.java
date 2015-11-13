import java.util.*; //it's way too tedious to do all the imports, just do this
import java.io.*;
import java.util.NoSuchElementException;
import java.lang.String;

//credit to http://algs4.cs.princeton.edu/41graph/AdjMatrixGraph.java
//for how to do the constructors
//credit to dr. ja
public class Graph<T> {
	private boolean[][] adjacencyMatrix;
	private int numVertices;
	private int numEdges;
	public Vector<GraphNode<Integer>> vertices = new Vector<GraphNode<Integer>>();

	//very specific to this situation
	public<T> Graph(String src) {
	try {

		BufferedReader in = new BufferedReader(new FileReader(src));
		String line;
		int numLines = 1;
		//Vector<Integer> toAdd = new Vector<Integer>();
		while((line = in.readLine()) != null) {
			Integer newNode = new Integer(line);
			vertices.add(new GraphNode<Integer>(newNode));
			System.out.println(newNode.toString());
			numLines += 1;
			//System.out.println(numLines);
		}
	
		adjacencyMatrix = new boolean[numLines][numLines];
		this.numVertices = numLines;
		//while (this.numEdges != numLines) {
			for (int i = 0; i < numLines; i++) {
				for (int j = 0; j < numLines; j++) {
					if ( i != j) {
						addEdge(i,j);
						//System.out.println("edge added at"+ i + ", " + j);
						//System.out.println("i now have "+ this.numEdges + " edges");
					}	

				}

			}
		//}

	}
	catch(FileNotFoundException e) {
		e.printStackTrace();
	}
	catch(IOException e) {
		e.printStackTrace();
	}
}

	public void addEdge(int x, int y) {
		if (!adjacencyMatrix[x][y]) {
			numEdges += 1;
		}
		adjacencyMatrix[x][y] = true;
		adjacencyMatrix[y][x] = true;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < numVertices; i++) {
			for (int j = 0; j < numVertices; j++) {
				if (adjacencyMatrix[i][j]) {
					s.append(" 1 ");
				}
				else {
					s.append(" 0 ");
				}
				
			}
			s.append("\n");
		}
		//s.append("\n\n");
		//for (Integer i : vertices) {
		//	s.append(i.toString() + " , ");
		//}
		return s.toString();
	}

	public class GraphNode<T> {
		public T val;
		public GraphNode next;
		public GraphNode prev;
		public boolean visited = false; //default false for all
		public GraphNode() {
			val = null;
			next = null;
			prev = null;

		}
		public GraphNode(T elem) {
			val = elem;
			next = null;
			prev = null;
		}
		public T elem() {
			return this.val;

		}

		public T next() {
			GraphNode gn = this.next;
			T val = (T)gn.elem();
			return val;
		}
		public T setElem(T value) {
			this.val = value;
			return val;

		}
		public void visitNode() {
			this.visited = true;
		}
		public void unVisitNode() {
			this.visited = false;
		}
	}
	public class Edge {
		protected GraphNode start;
		protected GraphNode end;
		protected double edgeWeight;

		public Edge(GraphNode st, GraphNode en, double w) {
			this.start = st;
			this.end = en;
			this.edgeWeight = w;
		}
		public double getWeight() {
			return this.edgeWeight;

		}
	}
	public static void main(String[] args) {
		Graph g = new Graph("fibonacci_short3.txt");
		System.out.println(g);

	}

}

