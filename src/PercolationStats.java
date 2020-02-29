import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double mean;
    private final double stddev;
    private final int t;

    public PercolationStats(int n, int t){
        if (n<=0||t<=0) {
            throw new IllegalArgumentException("illegal input argument");
        }
        this.t = t;
        double[] results = runMonteCarlo(n);
        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
    }
    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean-confidenceInterval();
    }

    public double confidenceHi() {
        return mean+confidenceInterval();
    }

    private double confidenceInterval() {
        return 1.96*stddev/Math.sqrt(this.t);
    }

    private double[] runMonteCarlo(int n){
        double[] results = new double[t];
        for(int i=0; i< t; i++){
            results[i] = runMonteCarloInstance(n);
        }
        return results;
    }

    private double runMonteCarloInstance(int n){
        Percolation percolation = new Percolation(n);
        double openSites = 0.0;
        while (!percolation.percolates()) {
            int i = StdRandom.uniform(1,n+1);
            int j = StdRandom.uniform(1,n+1);
            if (!percolation.isOpen(i, j)) {
                percolation.open(i,j);
                openSites++;
            }
        }
        return openSites/(n*n);

    }
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n,t);
        StdOut.println("mean = "+stats.mean());
        StdOut.println("stddev = "+stats.stddev());
        StdOut.println("95% confidence interval = "+stats.confidenceLo()+", "+stats.confidenceHi());
    }
}
