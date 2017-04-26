package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.NumericHandler;

public class ShortHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Short value = (Short) o;
        return value.compareTo(Short.valueOf((short) 0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new ShortHandler());
        }
    }
}
