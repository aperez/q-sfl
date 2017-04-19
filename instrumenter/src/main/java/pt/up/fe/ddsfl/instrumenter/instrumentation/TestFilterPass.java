package pt.up.fe.ddsfl.instrumenter.instrumentation;

import pt.up.fe.ddsfl.instrumenter.instrumentation.matchers.OrMatcher;
import pt.up.fe.ddsfl.instrumenter.instrumentation.matchers.BlackList;
import pt.up.fe.ddsfl.instrumenter.instrumentation.matchers.MethodAnnotationMatcher;
import pt.up.fe.ddsfl.instrumenter.instrumentation.matchers.SuperclassMatcher;

public class TestFilterPass extends FilterPass {

    public TestFilterPass() {
        BlackList junit3 = new BlackList(new SuperclassMatcher("junit.framework.TestCase"));
        BlackList junit4 = new BlackList(new OrMatcher(new MethodAnnotationMatcher("org.junit.Test"),
                new MethodAnnotationMatcher("org.junit.experimental.theories.Theory")));

        add(junit3);
        add(junit4);
    }
}
