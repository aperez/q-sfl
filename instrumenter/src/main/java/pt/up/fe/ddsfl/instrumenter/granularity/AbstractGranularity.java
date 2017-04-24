package pt.up.fe.ddsfl.instrumenter.granularity;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.instrumenter.model.NodeRetriever;

public abstract class AbstractGranularity implements Granularity {

    protected CtClass c;
    protected MethodInfo mi;
    protected CodeIterator ci;

    public AbstractGranularity(CtClass c, MethodInfo mi, CodeIterator ci) {
        this.c = c;
        this.mi = mi;
        this.ci = ci;
    }

    public Node getNode(CtClass cls, CtBehavior m, int line) {
        return NodeRetriever.getNode(cls, m, line);
    }
}