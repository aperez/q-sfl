package pt.up.fe.ddsfl.common.events;

import java.util.Arrays;

import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.model.Node;

public class VerboseEventListener implements EventListener {

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
        System.out.println("End Transaction " + transactionName + " " + isError + " " + Arrays.toString(activity));
    }

    @Override
    public void addNode(int id, String name, Node.Type type, int parentId, int line) {
        System.out.println("Add Node " + id + " " + name + " " + type + " " + parentId + " " + line);
    }

    @Override
    public void addProbe(int id, int nodeId) {
        System.out.println("Add Probe " + id + " " + nodeId);
    }

    @Override
    public void endSession() {
        System.out.println("End Session");
    }

    @Override
    public void handleMessage(Message message) {
        System.out.println("Received message");
    }

}
