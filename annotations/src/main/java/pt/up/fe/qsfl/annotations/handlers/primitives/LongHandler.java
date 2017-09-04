package pt.up.fe.qsfl.annotations.handlers.primitives;

import pt.up.fe.qsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.qsfl.annotations.handlers.NumericHandler;

public class LongHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Long value = (Long) o;
        return value.compareTo(Long.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new LongHandler());
        }
    }
}
