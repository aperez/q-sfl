package pt.up.fe.ddsfl.diagnoser.barinel;

import java.util.Deque;
import java.util.Iterator;

import pt.up.fe.ddsfl.common.spectrum.SpectrumFilter;
import pt.up.fe.ddsfl.diagnoser.ranking.SimilarityRanker;
import pt.up.fe.ddsfl.diagnoser.ranking.SimilarityRanker.SimilarityFunction;
import pt.up.fe.ddsfl.diagnoser.trie.Candidate;
import pt.up.fe.ddsfl.diagnoser.trie.Trie;
import pt.up.fe.ddsfl.diagnoser.util.StopCriteria;

public class MHS {

    private Trie candidates;
    private double heuristicThreshold = 0.05;
    private SimilarityFunction similarity = SimilarityRanker.OCHIAI; 

    public MHSStopCriteria stopCriteria;

    public MHS() {
        this.stopCriteria = new MHSStopCriteria();
    }

    public Trie generate(SpectrumFilter sf) {
        this.candidates = new Trie();
        sf = sf.copy();

        this.stopCriteria.start();
        sf.filterPassingTransactions();
        calculate_mhs(sf, new Candidate());

        return this.candidates;
    }

    private void calculate_mhs(SpectrumFilter sf, Candidate candidate) {		
        if(sf.hasFailingTransactions()) {
            if (sf.getComponentsSize() == 0 ||
                    this.stopCriteria.stop(candidate)) return;

            Deque<SimilarityRanker.Result> ranking = SimilarityRanker.rank(sf, similarity);

            Iterator<SimilarityRanker.Result> irr = ranking.descendingIterator();
            while(irr.hasNext()) {
                SimilarityRanker.Result rr = irr.next();
                if(rr.score <= this.heuristicThreshold) {
                    sf.filterComponent(rr.component);
                    irr.remove();
                }
                else break;
            }

            for(SimilarityRanker.Result rr : ranking) {
                SpectrumFilter newSf = sf.copy();
                newSf.stripComponent(rr.component);
                calculate_mhs(newSf, candidate.newCandidate(rr.component));

                sf.filterComponent(rr.component);
            }
        }
        else if (candidate.length() > 0) {
            candidate.sort();
            this.candidates.addCandidate(candidate);
        }
    }

    public static class MHSStopCriteria extends StopCriteria {
        public int maxCandidateLength = 6;

        public boolean stop(Candidate candidate) {
            return test(candidate.length(), maxCandidateLength) || stop();
        }
    }

}
