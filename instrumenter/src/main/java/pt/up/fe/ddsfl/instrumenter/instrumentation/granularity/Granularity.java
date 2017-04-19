package pt.up.fe.ddsfl.instrumenter.instrumentation.granularity;

import javassist.CtBehavior;
import javassist.CtClass;
import pt.up.fe.ddsfl.common.model.Node;

public interface Granularity {

    public boolean instrumentAtIndex(int index, int instrumentationSize);

    public boolean stopInstrumenting();

    public Node getNode(CtClass cls, CtBehavior m, int line);
}