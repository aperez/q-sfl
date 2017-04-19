package pt.up.fe.ddsfl.instrumenter.matchers;

import javassist.CtClass;
import javassist.CtMethod;

public interface Matcher {
    boolean matches(CtClass c);

    boolean matches(CtClass c, CtMethod m);
}