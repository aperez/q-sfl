package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.AbstractMessageReader;
import pt.up.fe.ddsfl.common.messaging.Message.AddProbeMessage;

public class ProbeReader extends AbstractMessageReader<AddProbeMessage> {

    public ProbeReader(String filename) {
        super(filename, AddProbeMessage.class);
    }

    @Override
    protected void dispatch(EventListener listener, AddProbeMessage object) {
        listener.addProbe(object.id, object.nodeId);
    }

}
