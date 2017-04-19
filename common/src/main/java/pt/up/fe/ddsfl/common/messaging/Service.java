package pt.up.fe.ddsfl.common.messaging;

import pt.up.fe.ddsfl.common.events.EventListener;

public interface Service {

    EventListener getEventListener();

    void interrupted();

    void terminated();

    public interface ServiceFactory {
        Service create(String id);
    }
}
