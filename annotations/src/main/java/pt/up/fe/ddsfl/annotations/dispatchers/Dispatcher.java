package pt.up.fe.ddsfl.annotations.dispatchers;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;

public interface Dispatcher {
    void setHandler(String type, LandmarkHandler handler);
    void setDefaultHandler(LandmarkHandler handler);
    LandmarkHandler getHandler(String type);
}
