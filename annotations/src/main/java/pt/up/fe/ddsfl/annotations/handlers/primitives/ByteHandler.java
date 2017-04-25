package pt.up.fe.ddsfl.annotations.handlers.primitives;

import pt.up.fe.ddsfl.annotations.handlers.NullableObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.NumericHandler;

public class ByteHandler extends NumericHandler {
    
    @Override
    public int handle(Object o) {
        Byte value = (Byte)o;
        return value.compareTo(Byte.valueOf((byte)0));
    }

    public static class BoxedHandler extends NullableObjectHandler {
        public BoxedHandler() {
            super(new ByteHandler());
        }
    }
}
