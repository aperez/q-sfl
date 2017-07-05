package pt.up.fe.ddsfl.instrumenter.runtime.data;

import pt.up.fe.ddsfl.common.messaging.Message;

public class ValueProbeMessages {

    public static class ByteMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public byte value;
        public final char t = 'b';

        public ByteMessage(int node, Byte value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class ShortMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public short value;
        public final char t = 's';

        public ShortMessage(int node, Short value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class IntegerMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public int value;
        public final char t = 'i';

        public IntegerMessage(int node, Integer value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class LongMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public long value;
        public final char t = 'l';

        public LongMessage(int node, Long value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class FloatMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public float value;
        public final char t = 'f';

        public FloatMessage(int node, Float value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class DoubleMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public double value;
        public final char t = 'd';

        public DoubleMessage(int node, Double value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class BooleanMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public boolean value;
        public final char t = 'z';

        public BooleanMessage(int node, Boolean value) {
            this.node = node;
            this.value = value;
        }
    }

    public static class ObjectMessage implements Message {

        private static final long serialVersionUID = -233901504257981850L;
        public int node;
        public boolean value;
        public final char t = 'o';

        public ObjectMessage(int node, boolean value) {
            this.node = node;
            this.value = value;
        }
    }
}
