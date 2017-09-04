package pt.up.fe.qsfl.common.spectrum;

import pt.up.fe.qsfl.common.events.EventListener;
import pt.up.fe.qsfl.common.messaging.Message;
import pt.up.fe.qsfl.common.messaging.MessageRecorder;
import pt.up.fe.qsfl.common.messaging.Message.EndTransactionMessage;
import pt.up.fe.qsfl.common.model.Node.Type;

public class TransactionRecorder extends MessageRecorder implements EventListener {

    public TransactionRecorder(String filename) {
        super(filename, true);
        transformBooleans();
    }

    @Override
    public void endTransaction(String transactionName, boolean[] activity, boolean isError) {
        writeMessage(new EndTransactionMessage(transactionName, activity, isError));
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
    }

}
