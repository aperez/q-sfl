package pt.up.fe.qsfl.annotations.handlers.primitives;

import pt.up.fe.qsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.qsfl.annotations.handlers.NumericHandler;

public class IntegerHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Integer value = (Integer) o;
        return value.compareTo(Integer.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new IntegerHandler());
        }
    }
}
