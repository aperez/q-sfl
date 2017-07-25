package pt.up.fe.ddsfl.diagnoser.util;

public class StopCriteria {

    public long timeout = 0;
    private long timestamp;

    private int iteration;
    public int maxIterations = 0;

    public StopCriteria() {
    }

    public void start() {
        this.timestamp = System.currentTimeMillis();
        this.iteration = 0;
    }

    private void iterate() {
        this.iteration++;
    }

    public boolean stop() {
        iterate();
        return test(System.currentTimeMillis() - timestamp, timeout) || 
                test(iteration, maxIterations);
    }

    protected boolean test(double variable, double max) {
        if (max > 0) {
            return variable >= max; 
        }
        return false;
    }
}
