package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.messaging.Message.AddNodeMessage;
import pt.up.fe.ddsfl.common.messaging.MessageRecorder;
import pt.up.fe.ddsfl.common.model.Node.Type;

public class NodeRecorder extends MessageRecorder implements EventListener {

    public NodeRecorder(String filename) {
        super(filename, true);
    }

    @Override
    public void addNode(int id, String name, Type type, int parentId, int line) {
        writeMessage(new AddNodeMessage(id, name, type, parentId, line));
    }

    @Override
    public void endSession() {
        close();
    }

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
    }

    @Override
    public void addProbe(int id, int nodeId) {
    }

    @Override
    public void handleMessage(Message message) {
    }

}
