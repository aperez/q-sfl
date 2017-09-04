package pt.up.fe.qsfl.annotations.handlers.primitives;

import pt.up.fe.qsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.qsfl.annotations.handlers.NumericHandler;

public class DoubleHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Double value = (Double) o;
        return value.compareTo(Double.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new DoubleHandler());
        }
    }
}
