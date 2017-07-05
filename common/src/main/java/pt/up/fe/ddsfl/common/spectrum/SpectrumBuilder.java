package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.model.Node;

public class SpectrumBuilder implements EventListener {

    protected SpectrumImpl spectrum;

    public SpectrumBuilder() {
        resetSpectrum();
    }

    public void resetSpectrum() {
        spectrum = new SpectrumImpl();
    }

    public Spectrum getSpectrum() {
        return spectrum;
    }

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
        spectrum.addTransaction(transactionName, activity, isError);
    }

    @Override
    public void addNode(int id, String name, Node.Type type, int parentId, int line) {
        spectrum.getTree().addNode(name, type, parentId, line);
    }

    @Override
    public void addProbe(int id, int nodeId) {
        spectrum.addProbe(id, nodeId);
    }

    @Override
    public void endSession() {
    }

    @Override
    public void handleMessage(Message message) {
    }

}
