package pt.up.fe.qsfl.common.spectrum;

import pt.up.fe.qsfl.common.events.EventListener;
import pt.up.fe.qsfl.common.messaging.AbstractMessageReader;
import pt.up.fe.qsfl.common.messaging.Message.AddProbeMessage;

public class ProbeReader extends AbstractMessageReader<AddProbeMessage> {

    public ProbeReader(String filename) {
        super(filename, AddProbeMessage.class);
    }

    @Override
    protected void dispatch(EventListener listener, AddProbeMessage object) {
        listener.addProbe(object.id, object.nodeId);
    }

}
