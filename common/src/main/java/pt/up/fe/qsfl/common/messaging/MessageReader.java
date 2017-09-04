package pt.up.fe.qsfl.common.messaging;

import pt.up.fe.qsfl.common.events.EventListener;

public interface MessageReader {

    public void read(EventListener listener);
    public void close();

}
