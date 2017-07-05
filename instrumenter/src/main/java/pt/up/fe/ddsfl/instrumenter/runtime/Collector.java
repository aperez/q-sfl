package pt.up.fe.ddsfl.instrumenter.runtime;

import java.util.List;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.events.MultiEventListener;
import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.common.model.Tree;
import pt.up.fe.ddsfl.instrumenter.agent.AgentConfigs;
import pt.up.fe.ddsfl.instrumenter.runtime.ProbeGroup.HitProbe;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbes;

public class Collector {

    private static Collector collector;

    private MultiEventListener listener;
    private Tree tree;
    private HitVector hitVector;
    //private SpectrumBuilder builder;
    private LandmarkVector landmarkVector;
    private AgentConfigs configs;

    public static Collector instance() {
        return collector;
    }

    public static Collector start(AgentConfigs configs) {
        if (collector == null) {
            collector = new Collector(configs.getEventListener());
            collector.setConfigs(configs);
        }
        return collector;
    }

    private Collector(EventListener listener) {
        this.listener = new MultiEventListener();
        //this.builder = new SpectrumBuilder();
        //addListener(this.builder);
        addListener(listener);

        this.tree = new Tree();
        this.hitVector = new HitVector();
        this.landmarkVector = new LandmarkVector();
    }

    private void setConfigs(AgentConfigs configs) {
        this.configs = configs;
    }

    public AgentConfigs getConfigs() {
        return this.configs;
    }

    public void addListener(EventListener listener) {
        if (listener != null) {
            this.listener.add(listener);
        }
    }

    //public SpectrumBuilder getBuilder() {
    //    return this.builder;
    //}

    public synchronized Node createNode(Node parent, String name, Node.Type type, int line) {
        Node node = tree.addNode(name, type, parent.getId(), line);
        listener.addNode(node.getId(), name, type, parent.getId(), line);
        return node;
    }

    public synchronized HitProbe createHitProbe(String groupName, int nodeId) {
        HitProbe p = hitVector.registerProbe(groupName, nodeId);
        listener.addProbe(p.getId(), p.getNodeId());
        return p;
    }

    public synchronized void endTransaction(String transactionName, boolean isError) {
        listener.endTransaction(transactionName, hitVector.get(), isError); // hitVector.get()
    }

    public synchronized void startTransaction() {
        ValueProbes.reset();
        hitVector.reset();
    }

    public synchronized void endSession() {
        // tree.print();
        listener.endSession();
    }

    public synchronized boolean[] getHitVector(String className) {
        return hitVector.get(className);
    }

    public synchronized boolean existsHitVector(String className) {
        return hitVector.existsHitVector(className);
    }

    public Node getRootNode() {
        return tree.getRoot();
    }

    public synchronized LandmarkHandler[] getLandmarkVector(String className) {
        return landmarkVector.getLandmarkVector(className);
    }

    public synchronized void addLandmarkVector(String className, List<LandmarkHandler> landmarks) {
        landmarkVector.addLandmarkVector(className, landmarks);
    }

    public synchronized void handleMessage(Message message) {
        listener.handleMessage(message);
    }

}
