package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message.AddProbeMessage;
import pt.up.fe.ddsfl.common.messaging.MessageReader;

public class ProbeReader extends MessageReader<AddProbeMessage> {

    public ProbeReader(String filename) {
        super(filename);
    }

    @Override
    protected void dispatch(EventListener listener, AddProbeMessage object) {
        listener.addProbe(object.id, object.nodeId);
    }

}
