package pt.up.fe.ddsfl.instrumenter.runtime.data;

import pt.up.fe.ddsfl.instrumenter.runtime.Collector;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.BooleanMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.ByteMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.DoubleMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.FloatMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.IntegerMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.LongMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.ObjectMessage;
import pt.up.fe.ddsfl.instrumenter.runtime.data.ValueProbeMessages.ShortMessage;

public class ValueProbes {

    public final static int MAX_PROBES = 5000;

    public static int counter = MAX_PROBES;
    public synchronized static void reset() {
        counter = MAX_PROBES;
    }

    public synchronized static void handleObject(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new ObjectMessage(node, object != null));
    }

    public synchronized static void handleByte(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new ByteMessage(node, (Byte)object));
    }

    public synchronized static void handleShort(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new ShortMessage(node, (Short)object));
    }

    public synchronized static void handleInteger(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new IntegerMessage(node, (Integer)object));
    }

    public synchronized static void handleLong(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new LongMessage(node, (Long)object));
    }

    public synchronized static void handleFloat(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new FloatMessage(node, (Float)object));
    }

    public synchronized static void handleDouble(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new DoubleMessage(node, (Double)object));
    }

    public synchronized static void handleBoolean(int node, Object object) {
        if (--counter < 0) { return; }
        Collector.instance().handleMessage(new BooleanMessage(node, (Boolean)object));
    }
}
