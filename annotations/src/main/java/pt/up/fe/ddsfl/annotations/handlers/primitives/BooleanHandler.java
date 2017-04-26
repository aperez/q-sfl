package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;

public class BooleanHandler implements LandmarkHandler {

    @Override
    public int handle(Object o) {
        boolean value = (Boolean) o;
        return value ? 0 : 1;
    }

    @Override
    public int landmarks() {
        return 2;
    }

    @Override
    public String getLandmarkName(int l) {
        if (l == 0) {
            return "false";
        }
        return "true";
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new BooleanHandler());
        }
    }
}
