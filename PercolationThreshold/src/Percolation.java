

//What is the largest value of n and the probability p where the fraction
//of open spaces from a to b is connected when spaces are randomly filled.

//Questions:
//How to use javac-algs4 and java-algs4 


//import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
	
	private static WeightedQuickUnionUF siteConnections;
	private static boolean[] siteAvail;
	private int gridLength;
	private int gridSize;
	private int openSites;
	
	public Percolation(int n){
		
		if (n < 1){
			throw new IllegalArgumentException(Integer.toString(n));
		} else {
			gridLength = n;
		}
		
		gridSize = (n * n) + 2;
		siteConnections = new WeightedQuickUnionUF(gridSize);
		siteAvail = new boolean[gridSize];
		
	}
	
	public void open (int row, int col){
		
		isCandRInBounds(row, col);
		
		int pos = getPos(row, col);
		
		if(siteAvail[pos] == false){
			
			siteAvail[pos] = true;
			openSites++;
		
			int left = pos - 1;
			if (leftExists(left) && siteAvail[left]){
				siteConnections.union(pos, left);
			}
			
			int right = pos + 1;
			if (rightExists(right) && siteAvail[right]){
				siteConnections.union(pos, right);
			}
			
			int below = pos - gridLength;
			if (belowExists(below) && siteAvail[below]){
				siteConnections.union(pos, below);
			}
			
			int above = pos + gridLength;
			if (aboveExists(above) && siteAvail[above]){
				siteConnections.union(pos, above);
			}
			
			if(topRow(pos)){
				siteConnections.union(pos, 0);
			}
			
			if(bottomRow(pos)){
				siteConnections.union(pos, gridSize - 1);
			}
		
		}
					
	}
	
	public boolean isFull(int row, int col){
		isCandRInBounds(row, col);
		return siteConnections.connected(getPos(row, col), 0);
	}
	
	public int numberOfOpenSites(){
		return openSites;
	}
	
	public boolean percolates(){
		return siteConnections.connected(0, gridSize - 1);
	}
	
	private int getPos(int row, int col){
		return ((row - 1) * gridLength) + (col);  
	}
	
	private boolean leftExists(int pos){
		return pos > 0 && pos % gridLength != 0;
	}
	
	private boolean rightExists(int pos){
		return pos < gridSize - 1 && pos % gridLength != 1;
	}
	
	private boolean belowExists(int pos){
		return pos > 0;
	}
	
	private boolean aboveExists(int pos){
		return pos < gridSize - 1;
	}
	
	public boolean isOpen(int row, int col){
		isCandRInBounds(row, col);

		return siteAvail[getPos(row, col)];
	}
	
	private boolean bottomRow(int pos) {
		return gridSize - 1 > pos && pos >= gridSize - 1 - gridLength;
	}

	private boolean topRow(int pos) {
		return pos > 0 && pos <= gridLength;
	}
	
	private void isCandRInBounds(int row, int col) {
		if (row < 1 || row > gridLength || col < 1 || col > gridLength){
			throw new IndexOutOfBoundsException("Row or Column must be between 1 and " + gridLength); 
		}
	}
	
}
