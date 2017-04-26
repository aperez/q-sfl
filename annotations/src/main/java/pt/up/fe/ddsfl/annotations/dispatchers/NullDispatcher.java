package pt.up.fe.ddsfl.annotations.dispatchers;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;

public class NullDispatcher implements Dispatcher {

    @Override
    public LandmarkHandler getHandler(String type, boolean isReturn) {
        return null;
    }

    @Override
    public boolean overrideAnnotations() {
        return true;
    }

}
