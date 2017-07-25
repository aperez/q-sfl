package pt.up.fe.ddsfl.diagnoser.ranking;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

import pt.up.fe.ddsfl.common.spectrum.SpectrumFilter;

public class SimilarityRanker {

    public static interface SimilarityFunction {
        public double compute(int[][] n);
    }

    public final static SimilarityFunction OCHIAI = new SimilarityFunction() {
        @Override
        public double compute(int[][] n) {
            double res = Math.sqrt(n[1][1] + n[0][1]) * Math.sqrt(n[1][1] + n[1][0]);

            if (res == 0.0)
                return 0;
            else 
                return (float) n[1][1] / res;
        }
    };

    public static class Result implements Comparable<Result> {
        public final Integer component;
        public final Double score;
        public Result(int component, double score) {
            this.component = component;
            this.score = score;
        }

        @Override
        public int compareTo(Result rr) {
            return rr.score.compareTo(this.score);
        }
    }

    public static Deque<Result> rank(SpectrumFilter sf, SimilarityFunction sim) {

        LinkedList<Result> results = new LinkedList<Result>();

        for(int c = 0; c < sf.getComponentsSize(); c++) {
            int[][] n = new int[][]{{0,0},{0,0}};

            for(int t = 0; t < sf.getTransactionsSize(); t++) {
                n[sf.isInvolved(t, c) ? 1 : 0][sf.isError(t) ? 1 : 0]++;
            }

            results.add(new Result(sf.getComponent(c), sim.compute(n)));
        }
        Collections.sort(results);

        return results;
    }
}
