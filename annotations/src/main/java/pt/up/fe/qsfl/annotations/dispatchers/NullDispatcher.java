package pt.up.fe.qsfl.annotations.dispatchers;

import pt.up.fe.qsfl.annotations.handlers.LandmarkHandler;

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
