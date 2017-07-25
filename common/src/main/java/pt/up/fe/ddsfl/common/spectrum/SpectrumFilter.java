package pt.up.fe.ddsfl.common.spectrum;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pt.up.fe.ddsfl.common.spectrum.Spectrum;

public class SpectrumFilter {

    protected List<Integer> componentsFilter;
    protected List<Integer> transactionsFilter;
    protected Spectrum delegate;

    public SpectrumFilter(Spectrum spectra) {
        this.delegate = spectra;

        this.componentsFilter = new LinkedList<Integer>();
        for(int c = 0; c < spectra.getComponentsSize(); c++) {
            this.componentsFilter.add(c);
        }

        this.transactionsFilter = new LinkedList<Integer>();
        for(int t = 0; t < spectra.getTransactionsSize(); t++) {
            this.transactionsFilter.add(t);
        }
    }

    public SpectrumFilter(SpectrumFilter spectrumFilter) {
        this.delegate = spectrumFilter.delegate;
        this.componentsFilter = new LinkedList<Integer>(spectrumFilter.componentsFilter);
        this.transactionsFilter = new LinkedList<Integer>(spectrumFilter.transactionsFilter);
    }

    public SpectrumFilter copy() {
        return new SpectrumFilter(this);
    }

    public void stripComponent(int c) {
        Iterator<Integer> i = this.transactionsFilter.iterator();
        while(i.hasNext()) {
            int t = i.next();

            if (this.delegate.isInvolved(t,c)) {
                i.remove();
            }
        }

        filterComponent(c);
    }

    public void filterComponent(int c) {
        this.componentsFilter.remove((Integer) c);
    }

    public void filterTransaction(int t) {
        this.transactionsFilter.remove((Integer) t);
    }

    public void filterPassingTransactions() {
        Iterator<Integer> i = this.transactionsFilter.iterator();
        while(i.hasNext()) {
            int t = i.next();

            if(!this.delegate.isError(t)) {
                i.remove();
            }
        }
    }

    public boolean hasFailingTransactions() {
        Iterator<Integer> i = this.transactionsFilter.iterator();
        while(i.hasNext()) {
            int t = i.next();

            if(this.delegate.isError(t)) {
                return true;
            }
        }
        return false;
    }

    public int getComponent(int c) {
        return this.componentsFilter.get(c);
    }

    public int getComponentsSize() {
        return this.componentsFilter.size();
    }

    public int getTransactionsSize() {
        return this.transactionsFilter.size();
    }

    public boolean isInvolved(int t, int c) {
        return delegate.isInvolved(this.transactionsFilter.get(t),
                this.componentsFilter.get(c));
    }

    public boolean isUnfilteredComponentInvolved(int t, int c) {
        //assert(this.componentsFilter.indexOf(c) != -1);
        return delegate.isInvolved(this.transactionsFilter.get(t), c);
    }

    public boolean isError(int t) {
        return delegate.isError(this.transactionsFilter.get(t));
    }
}
