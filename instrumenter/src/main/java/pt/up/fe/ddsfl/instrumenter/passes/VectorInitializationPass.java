package pt.up.fe.ddsfl.instrumenter.passes;

import java.lang.reflect.Modifier;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;

/*
 * This pass ensures that the error and landmark vectors are initialized
 * by making extra calls to Collector.get___Vector in constructors and
 * static methods.
 * Fixes this issue: https://github.com/jboss-javassist/javassist/issues/71
 */
public class VectorInitializationPass implements Pass {

    @Override
    public Outcome transform(CtClass c) throws Exception {

        try {
            c.getDeclaredField(InstrumentationPass.HIT_VECTOR_NAME);
            c.getDeclaredField(LandmarkInserterPass.LANDMARK_VECTOR_NAME);
        } catch (Exception e) {
            return Outcome.CANCEL;
        }

        for (CtBehavior b : c.getDeclaredBehaviors()) {
            try {
                handleBehavior(c, b);
            } catch (Exception e) {
            }
        }

        return Outcome.CONTINUE;
    }

    private void handleBehavior(CtClass c, CtBehavior b) throws Exception {
        if (InstrumentationPass.toSkip(c, b)) {
            return;
        }

        String instrumentationStr = InstrumentationPass.getVectorInitializer(c) +
                LandmarkInserterPass.getVectorInitializer(c);

        if (b instanceof CtConstructor) {
            CtConstructor constructor = (CtConstructor) b;
            constructor.insertBeforeBody(instrumentationStr);
        } else if (Modifier.isStatic(b.getModifiers())) {
            b.insertBefore(instrumentationStr);
        }
    }

}
