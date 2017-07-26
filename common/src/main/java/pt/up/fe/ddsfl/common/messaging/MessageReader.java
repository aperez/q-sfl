package pt.up.fe.ddsfl.common.messaging;

import pt.up.fe.ddsfl.common.events.EventListener;

public interface MessageReader {

    public void read(EventListener listener);
    public void close();

}
