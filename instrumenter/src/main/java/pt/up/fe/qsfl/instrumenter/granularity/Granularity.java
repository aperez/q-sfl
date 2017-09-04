package pt.up.fe.qsfl.instrumenter.granularity;

import javassist.CtBehavior;
import javassist.CtClass;
import pt.up.fe.qsfl.common.model.Node;

public interface Granularity {

    public boolean instrumentAtIndex(int index, int instrumentationSize);

    public boolean stopInstrumenting();

    public Node getNode(CtClass cls, CtBehavior m, int line);
}