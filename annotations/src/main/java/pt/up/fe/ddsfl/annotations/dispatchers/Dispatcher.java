package pt.up.fe.ddsfl.annotations.dispatchers;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;

public interface Dispatcher {
    LandmarkHandler getHandler(String type, boolean isReturn);
    boolean overrideAnnotations();
}
