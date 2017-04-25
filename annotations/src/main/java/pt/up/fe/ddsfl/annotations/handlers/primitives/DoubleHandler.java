package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.NumericHandler;

public class DoubleHandler extends NumericHandler {

    @Override
    public int handle(Object o) {
        Double value = (Double)o;
        return value.compareTo(Double.valueOf(0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new DoubleHandler());
        }
    }
}
