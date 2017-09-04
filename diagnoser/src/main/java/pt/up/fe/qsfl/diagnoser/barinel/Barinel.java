package pt.up.fe.qsfl.diagnoser.barinel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import pt.up.fe.qsfl.common.spectrum.SpectrumFilter;
import pt.up.fe.qsfl.diagnoser.trie.Candidate;
import pt.up.fe.qsfl.diagnoser.trie.Trie;
import pt.up.fe.qsfl.diagnoser.trie.Candidate.CandidateIterator;

public class Barinel {

    private SpectrumFilter spectrum;	
    public double lambda = 1.0;
    public double epsilon = 0.0001;
    public double pr = 0.01;

    public Barinel() {
    }

    public List<Result> calculate(SpectrumFilter spectrum, Trie candidates) {
        this.spectrum = spectrum;
        List<Result> results = new ArrayList<Result>();
        double total = 0;

        for (Candidate candidate : candidates) {
            double prob = candidatePropability(candidate);
            total += prob;
            results.add(new Result(candidate, prob));
        }

        for (Result r : results) {
            r.probability /= total;
        }
        Collections.sort(results);

        return results;
    }

    private double candidatePropability(Candidate candidate) {
        GoodnessModel gm = new GoodnessModel(this.spectrum, candidate);

        double[] goodnesses = new double[candidate.length()];
        Arrays.fill(goodnesses, 0.5);

        double prob = probability(candidate, goodnesses);
        double oldProb;

        do {
            oldProb = prob;

            goodnesses = updateGoodnesses(gm, goodnesses);
            prob = probability(candidate, goodnesses);

        } while (!stop(prob, oldProb));

        return prob;
    }

    private boolean stop(double prob, double oldProb) {
        return 2.0 * (prob - oldProb) / Math.abs(prob + oldProb) < this.epsilon;
    }

    private double probability(Candidate candidate, double[] goodnesses) {
        double prob = 1.0;

        for(int t = 0; t < this.spectrum.getTransactionsSize(); t++) {
            double tmp = 1.0;

            for (CandidateIterator iter = candidate.iterator(); iter.hasNext(); iter.next()) {
                if (this.spectrum.isUnfilteredComponentInvolved(t, iter.peek())) {
                    tmp *= goodnesses[iter.peekIndex()];
                }
            }

            if(this.spectrum.isError(t)) {
                tmp = 1.0 - tmp;
            }

            prob *= tmp;
        }

        prob *= prior(candidate);

        return prob;
    }

    private double prior(Candidate candidate) {
        return Math.pow(this.pr, candidate.length());
    }

    private double[] updateGoodnesses(GoodnessModel gm, double[] goodnesses) {
        double[] grad = gradient(gm, goodnesses);
        return update(goodnesses, grad);
    }

    private double[] gradient(GoodnessModel gm, double[] goodnesses) {
        int candidateSize = goodnesses.length;
        double[] grad = new double[candidateSize];
        Arrays.fill(grad, 0);

        for (int pattern = 0; pattern < 1 << candidateSize; pattern++) {
            double passes = (double) gm.passes[pattern];
            double fails = (double) gm.fails[pattern];

            if (passes == 0 && fails == 0)
                continue;

            double tmp = 1.0;

            for(int i = 0; i < candidateSize; i++) {
                grad[i] += passes / goodnesses[i];
                if ( (pattern & (1 << i)) != 0 ) {
                    tmp *= goodnesses[i];
                }
            }

            for(int i = 0; i < candidateSize; i++) {
                if ( (pattern & (1 << i)) != 0 ) {
                    grad[i] += -fails * (tmp / goodnesses[i]) * (1.0 - tmp);
                }
            }
        }

        return grad;
    }

    private double[] update(double[] goodnesses, double[] grad) {
        double[] newGoodnesses = Arrays.copyOf(goodnesses, goodnesses.length);

        for (int i = 0; i < goodnesses.length; i++) {
            if (grad[i] == 0)
                continue;

            double goodness = goodnesses[i];
            newGoodnesses[i] = goodness + this.lambda * goodness / grad[i];

            if (newGoodnesses[i] <= 0)
                newGoodnesses[i] = this.epsilon;
            else if (newGoodnesses[i] > 1)
                newGoodnesses[i] = 1;
        }

        return newGoodnesses;
    }

    public static class Result implements Comparable<Result> {
        public Candidate candidate;
        public Double probability;

        public Result(Candidate candidate, double probability) {
            this.candidate = candidate;
            this.probability = probability;
        }

        @Override
        public String toString() {
            return this.candidate.toString() + " " + probability;
        }

        @Override
        public int compareTo(Result o) {
            return o.probability.compareTo(this.probability);
        }
    }

    private static class GoodnessModel {
        public final int[] passes;
        public final int[] fails;

        public GoodnessModel(SpectrumFilter spectrum, Candidate candidate) {
            int candidateLength = candidate.length();

            this.passes = new int[1 << candidateLength];
            this.fails = new int[1 << candidateLength];

            Arrays.fill(this.passes, 0);
            Arrays.fill(this.fails, 0);

            for(int t = 0; t < spectrum.getTransactionsSize(); t++) {
                int pattern = 0;

                for (CandidateIterator iter = candidate.iterator(); iter.hasNext(); iter.next()) {
                    int component = iter.peek();
                    if (spectrum.isUnfilteredComponentInvolved(t, component)) {
                        pattern += (1 << iter.peekIndex());
                    }
                }

                if (pattern != 0) {
                    if (spectrum.isError(t)) 
                        this.fails[pattern]++;
                    else
                        this.passes[pattern]++;
                }
            }
        }
    }
}
