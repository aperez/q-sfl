package pt.up.fe.ddsfl.instrumenter.runtime.data;

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

public class ValueProbeDispatcher {

    private Map<String, String> typeToHandler;
    private String defaultHandler;

    public ValueProbeDispatcher() {
        defaultHandler = "handleObject";

        typeToHandler = new HashMap<String, String>();
        typeToHandler.put(byte.class.getName(), "handleByte");
        typeToHandler.put(short.class.getName(), "handleShort");
        typeToHandler.put(int.class.getName(), "handleInteger");
        typeToHandler.put(long.class.getName(), "handleLong");
        typeToHandler.put(float.class.getName(), "handleFloat");
        typeToHandler.put(double.class.getName(), "handleDouble");
        typeToHandler.put(boolean.class.getName(), "handleBoolean");
    }

    public String getInstrumentationString(CtClass c, int nodeId, String variableName) {
        if (c == CtClass.voidType) {
            return null;
        }

        String handler = typeToHandler.get(c.getName());
        if (handler == null) {
            handler = defaultHandler;
        }

        return "{"+ValueProbes.class.getCanonicalName()+"."+handler+"("+nodeId+","+variableName+");}";
    }

}
