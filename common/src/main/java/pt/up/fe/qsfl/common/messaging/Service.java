package pt.up.fe.qsfl.common.messaging;

import pt.up.fe.qsfl.common.events.EventListener;

public interface Service {

    EventListener getEventListener();

    void interrupted();

    void terminated();

    public interface ServiceFactory {
        Service create(String id);
    }
}
