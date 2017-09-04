package pt.up.fe.qsfl.common.spectrum;

import pt.up.fe.qsfl.common.events.EventListener;
import pt.up.fe.qsfl.common.messaging.Message;
import pt.up.fe.qsfl.common.messaging.MessageRecorder;
import pt.up.fe.qsfl.common.messaging.Message.AddProbeMessage;
import pt.up.fe.qsfl.common.model.Node.Type;

public class ProbeRecorder extends MessageRecorder implements EventListener {

    public ProbeRecorder(String filename) {
        super(filename, true);
    }

    @Override
    public void addProbe(int id, int nodeId) {
        writeMessage(new AddProbeMessage(id, nodeId));
    }

    @Override
    public void endSession() {
        close();
    }

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {  
    }

    @Override
    public void addNode(int id, String name, Type type, int parentId, int line) {
    }

    @Override
    public void handleMessage(Message message) {
    }
}
