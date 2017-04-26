package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.NumericHandler;

public class FloatHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Float value = (Float) o;
        return value.compareTo(Float.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new FloatHandler());
        }
    }
}
