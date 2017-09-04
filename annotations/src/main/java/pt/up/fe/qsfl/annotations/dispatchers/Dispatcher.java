package pt.up.fe.qsfl.annotations.dispatchers;

import pt.up.fe.qsfl.annotations.handlers.LandmarkHandler;

public interface Dispatcher {
    LandmarkHandler getHandler(String type, boolean isReturn);
    boolean overrideAnnotations();
}
