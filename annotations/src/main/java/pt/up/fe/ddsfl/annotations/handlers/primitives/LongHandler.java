package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.NumericHandler;

public class LongHandler extends NumericHandler {

    @Override
    public int handle(Object o) {
        Long value = (Long)o;
        return value.compareTo(Long.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new LongHandler());
        }
    }
}
