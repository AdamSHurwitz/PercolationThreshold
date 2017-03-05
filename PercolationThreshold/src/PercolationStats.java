import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private int trials;
	private double percThreshold;
	private double[] ptArray;
	
	public static void main(String[] args){
			
		PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

		System.out.println("mean                    = " + String.valueOf(percolationStats.mean()));
		System.out.println("stddev                  = " + String.valueOf(percolationStats.stddev()));
		System.out.println("95% confidence interval = [" + String.valueOf(percolationStats.confidenceLo()) + ", " 
		+ String.valueOf(percolationStats.confidenceHi()) + "]");
	}      
	   
	public PercolationStats(int n, int trials){
		
		if (n < 1 || trials < 1){
			new IllegalArgumentException();
		}

		this.trials = trials;
		
		ptArray = new double[trials];
		
		for ( int i = 0; i < trials; i++){
		
			Percolation percolation = new Percolation(n);
			
			boolean percolate = false;
			
			while (!percolate){
				
				int row = randRow(n + 1);
				int col = randRow(n + 1);
				percolation.open(row, col);
			
			
				if (percolation.percolates()){
					percolate = true;
					percThreshold = (double) percolation.numberOfOpenSites() / (n * n);
					ptArray[i] = percThreshold;
				}
			}
		}
		
	}    
		
	public double mean(){
		return StdStats.mean(ptArray);
	}                          
		
	public double stddev(){
		return StdStats.stddev(ptArray);
	}                        
		
	public double confidenceLo(){
		return mean() - (1.96 * stddev() / Math.sqrt(trials));
	}                  
		
	public double confidenceHi(){
		return mean() + (1.96 * stddev() / Math.sqrt(trials));
	}	   
	
	private int randRow(int gridLength){
		return StdRandom.uniform(1, gridLength);
	}

	private int randCol(int gridLength){
		return StdRandom.uniform(1, gridLength); 
	}
}
