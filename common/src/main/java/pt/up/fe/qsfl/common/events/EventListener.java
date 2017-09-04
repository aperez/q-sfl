package pt.up.fe.qsfl.common.events;

import pt.up.fe.qsfl.common.messaging.Message;
import pt.up.fe.qsfl.common.model.Node;

public interface EventListener {

    void endTransaction(String transactionName, boolean[] activity, boolean isError);

    void addNode(int id, String name, Node.Type type, int parentId, int line);

    void addProbe(int id, int nodeId);

    void endSession();

    void handleMessage(Message message);
}
