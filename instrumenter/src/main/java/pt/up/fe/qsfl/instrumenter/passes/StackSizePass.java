package pt.up.fe.qsfl.instrumenter.passes;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.MethodInfo;

public class StackSizePass implements Pass {

    @Override
    public final Outcome transform(CtClass c) throws Exception {
        try {
            for (CtBehavior b : c.getDeclaredBehaviors()) {
                MethodInfo info = b.getMethodInfo();
                CodeAttribute ca = info.getCodeAttribute();

                if (ca != null) {
                    int ss = ca.computeMaxStack();
                    ca.setMaxStack(ss);
                }
            }
        }
        catch (Exception e) {
            return Outcome.CANCEL;
        }


        return Outcome.CONTINUE;
    }
}