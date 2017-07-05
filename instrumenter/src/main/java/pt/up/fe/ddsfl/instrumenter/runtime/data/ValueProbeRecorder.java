package pt.up.fe.ddsfl.instrumenter.runtime.data;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.messaging.Message.EndTransactionMessage;
import pt.up.fe.ddsfl.common.messaging.MessageRecorder;
import pt.up.fe.ddsfl.common.model.Node.Type;

public class ValueProbeRecorder extends MessageRecorder implements EventListener {

    public ValueProbeRecorder(String filename) {
        super(filename, true);
        transformBooleans();
    }

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
        writeMessage(new EndTransactionMessage(transactionName, null, isError));
    }

    @Override
    public void endSession() {
        close();
    }

    @Override
    public void addNode(int id, String name, Type type, int parentId, int line) {
    }

    @Override
    public void addProbe(int id, int nodeId) {
    }

    @Override
    public void handleMessage(Message message) {
        writeMessage(message);
    }

}
