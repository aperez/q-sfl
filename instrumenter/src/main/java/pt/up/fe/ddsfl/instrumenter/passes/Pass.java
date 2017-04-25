package pt.up.fe.ddsfl.instrumenter.passes;

import javassist.CtClass;

public interface Pass {
    public static enum Outcome {
        CONTINUE, CANCEL, FINISH
    }

    ;

    Outcome transform(CtClass c) throws Exception;
}