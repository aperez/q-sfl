package pt.up.fe.qsfl.instrumenter.runtime.data;

import java.util.Arrays;

import pt.up.fe.qsfl.instrumenter.runtime.Collector;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.BooleanMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.ByteMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.DoubleMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.FloatMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.IntegerMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.LongMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.ObjectMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.ShortMessage;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeMessages.StringSizeMessage;

public class ValueProbes {

    public static int[] counters = new int[9];

    public synchronized static void reset(int maxProbes) {
        Arrays.fill(counters, maxProbes);
    }

    public synchronized static void handleObject(int node, Object object) {
        if (--counters[0] < 0) { return; }
        Collector.instance().handleMessage(new ObjectMessage(node, object != null));
    }

    public synchronized static void handleString(int node, Object object) {
        if (--counters[1] < 0) { return; }
        Collector.instance().handleMessage(new StringSizeMessage(node, object == null ? -1 : ((String)object).length()));
    }

    public synchronized static void handleByte(int node, Object object) {
        if (--counters[2] < 0) { return; }
        Collector.instance().handleMessage(new ByteMessage(node, (Byte)object));
    }

    public synchronized static void handleShort(int node, Object object) {
        if (--counters[3] < 0) { return; }
        Collector.instance().handleMessage(new ShortMessage(node, (Short)object));
    }

    public synchronized static void handleInteger(int node, Object object) {
        if (--counters[4] < 0) { return; }
        Collector.instance().handleMessage(new IntegerMessage(node, (Integer)object));
    }

    public synchronized static void handleLong(int node, Object object) {
        if (--counters[5] < 0) { return; }
        Collector.instance().handleMessage(new LongMessage(node, (Long)object));
    }

    public synchronized static void handleFloat(int node, Object object) {
        if (--counters[6] < 0) { return; }
        Collector.instance().handleMessage(new FloatMessage(node, (Float)object));
    }

    public synchronized static void handleDouble(int node, Object object) {
        if (--counters[7] < 0) { return; }
        Collector.instance().handleMessage(new DoubleMessage(node, (Double)object));
    }

    public synchronized static void handleBoolean(int node, Object object) {
        if (--counters[8] < 0) { return; }
        Collector.instance().handleMessage(new BooleanMessage(node, (Boolean)object));
    }
}
