package pt.up.fe.ddsfl.diagnoser.trie;

import java.util.Arrays;
import java.util.Iterator;

public class Candidate implements Iterable<Integer> {

    private int[] candidate;

    public Candidate(int... candidate) {
        this.candidate = candidate;
    }

    public Candidate(Candidate c, int... newComponents) {
        int oldLength = c.candidate.length;
        int newLength = newComponents.length;

        this.candidate = Arrays.copyOf(c.candidate, oldLength + newLength);

        for(int i = 0; i < newLength; i++) {
            this.candidate[oldLength + i] = newComponents[i];
        }
    }

    public Candidate newCandidate(int... components) {
        return new Candidate(this, components);
    }

    public void sort() {
        Arrays.sort(this.candidate);
    }

    public int length() {
        return candidate.length;
    }

    /*public int get(int pos) {
		return candidate[pos];
	}*/

    @Override
    public String toString() {
        return Arrays.toString(this.candidate);
    }

    @Override
    public CandidateIterator iterator() {
        return new CandidateIterator(this);
    }

    public static class CandidateIterator implements Iterator<Integer> {

        private Candidate delegate;
        private int pos;

        public CandidateIterator(Candidate candidate) {
            this(candidate, -1);
        }

        public CandidateIterator(Candidate candidate, int pos) {
            this.delegate = candidate;
            this.pos = pos;
        }

        public CandidateIterator copy() {
            return new CandidateIterator(this.delegate, this.pos);
        }

        @Override
        public boolean hasNext() {
            return this.delegate.candidate.length > pos+1;
        }

        @Override
        public Integer next() {
            return this.delegate.candidate[++pos];
        }

        public Integer peek() {
            return this.delegate.candidate[pos+1];
        }

        public Integer peekIndex() {
            return pos+1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
}
