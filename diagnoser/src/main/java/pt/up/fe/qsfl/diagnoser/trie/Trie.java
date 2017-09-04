package pt.up.fe.qsfl.diagnoser.trie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pt.up.fe.qsfl.diagnoser.trie.Candidate.CandidateIterator;

import java.util.TreeMap;

public class Trie implements Iterable<Candidate> {

    private Map<Integer, Trie> children;
    private boolean terminal;

    public Trie() {
        this.children = new TreeMap<Integer, Trie>();
        this.terminal = false;
    }

    public void addCandidate(Candidate candidate) {
        if(!isSubsumed(candidate)) {
            removeSubsumed(candidate);
            add(candidate);
        }
    }

    private void add(Candidate candidate) {
        add(candidate.iterator());
    }

    private void add(CandidateIterator iter) {
        if(!iter.hasNext()) {
            this.terminal = true;
        }
        else {
            int component = iter.next();

            Trie child = this.children.get(component);

            if (child == null) {
                child = new Trie();
                this.children.put(component, child);
            }

            child.add(iter);
        }
    }

    public boolean isSubsumed(Candidate candidate) {
        return isSubsumed(candidate.iterator());
    }

    private boolean isSubsumed(CandidateIterator iter) {
        if (!iter.hasNext()) {
            return true;
        }
        else {
            while (iter.hasNext()) {
                int component = iter.peek();

                Trie child = this.children.get(component);

                if (child != null) {
                    if (child.isSubsumed(iter.copy())) {
                        return true;
                    }
                }
                iter.next();
            }
        }
        return false;
    }

    public void removeSubsumed(Candidate candidate) {
        if (candidate.length() == 0) {
            this.children.clear();
            this.terminal = true;
            return;
        }

        removeSubsumed(candidate.iterator());
    }

    private boolean removeSubsumed(CandidateIterator iter) {
        if(!iter.hasNext()) {
            this.terminal = true;
            return true;
        }

        int component = iter.peek();
        Iterator<Entry<Integer, Trie>> ientry = this.children.entrySet().iterator();

        while(ientry.hasNext()) {
            Entry<Integer, Trie> entry = ientry.next();
            int key = entry.getKey();

            if (key > component)
                continue;

            CandidateIterator ci = iter.copy();
            if (key == component)
                ci.next();

            if (entry.getValue().removeSubsumed(ci))
                ientry.remove();
        }

        return !this.terminal && this.children.isEmpty();
    }

    public void print(String padding) {
        for (Entry<Integer,Trie> entry : this.children.entrySet()) {
            System.out.println(padding+" "+entry.getKey());
            entry.getValue().print(padding + "--");
        }
    }

    public List<Candidate> getCandidates() {
        List<Candidate> candidates = new ArrayList<Candidate>();
        getCandidates(candidates, new Candidate());

        return candidates;
    }

    private void getCandidates(List<Candidate> candidates, Candidate candidate) {
        if(this.terminal) {
            candidates.add(candidate);
        }
        else {
            for(Entry<Integer, Trie> entry : this.children.entrySet()) {
                entry.getValue().getCandidates(candidates, new Candidate(candidate, entry.getKey()));
            }
        }
    }

    @Override
    public Iterator<Candidate> iterator() {
        return this.getCandidates().iterator();
    }
}
