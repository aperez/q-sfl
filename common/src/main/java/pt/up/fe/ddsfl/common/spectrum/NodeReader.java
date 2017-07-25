package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message.AddNodeMessage;
import pt.up.fe.ddsfl.common.messaging.MessageReader;

public class NodeReader extends MessageReader<AddNodeMessage> {

    public NodeReader(String filename) {
        super(filename, AddNodeMessage.class);
    }

    @Override
    protected void dispatch(EventListener listener, AddNodeMessage object) {
        listener.addNode(object.id, object.name, object.type, object.parentId, object.line);
    }

}
