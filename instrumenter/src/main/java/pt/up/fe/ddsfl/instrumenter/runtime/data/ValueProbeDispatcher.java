package pt.up.fe.ddsfl.instrumenter.runtime.data;

import javassist.CtClass;

public class ValueProbeDispatcher {

    public String getInstrumentationString(CtClass c, int nodeId, String variableName) {
        if (c != CtClass.voidType) {
            return "{"+ValueProbe.class.getCanonicalName()+".handle("+nodeId+","+variableName+");}";
        }
        return null;
    }

}
