package pt.up.fe.qsfl.annotations.handlers.primitives;

import pt.up.fe.qsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.qsfl.annotations.handlers.NumericHandler;

public class ByteHandler extends NumericHandler {

    @Override
    protected int compareWithZero(Object o) {
        Byte value = (Byte) o;
        return value.compareTo(Byte.valueOf((byte) 0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new ByteHandler());
        }
    }
}
