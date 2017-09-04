package pt.up.fe.qsfl.common.spectrum;

import pt.up.fe.qsfl.common.events.EventListener;
import pt.up.fe.qsfl.common.messaging.AbstractMessageReader;
import pt.up.fe.qsfl.common.messaging.Message.AddNodeMessage;

public class NodeReader extends AbstractMessageReader<AddNodeMessage> {

    public NodeReader(String filename) {
        super(filename, AddNodeMessage.class);
    }

    @Override
    protected void dispatch(EventListener listener, AddNodeMessage object) {
        listener.addNode(object.id, object.name, object.type, object.parentId, object.line);
    }

}
