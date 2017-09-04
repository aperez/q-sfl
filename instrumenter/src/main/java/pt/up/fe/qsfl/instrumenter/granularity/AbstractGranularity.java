package pt.up.fe.qsfl.instrumenter.granularity;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.CodeIterator;
import javassist.bytecode.MethodInfo;
import pt.up.fe.qsfl.common.model.Node;
import pt.up.fe.qsfl.instrumenter.model.NodeRetriever;

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