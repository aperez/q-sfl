package pt.up.fe.ddsfl.common.events;

import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.model.Node.Type;

public class NullEventListener implements EventListener {

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
    }

    @Override
    public void addNode(int id, String name, Type type, int parentId, int line) {
    }

    @Override
    public void addProbe(int id, int nodeId) {
    }

    @Override
    public void endSession() {
    }

    @Override
    public void handleMessage(Message message) {
    }

}
