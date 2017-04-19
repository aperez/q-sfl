package pt.up.fe.ddsfl.instrumenter.passes;

import pt.up.fe.ddsfl.instrumenter.matchers.BlackList;
import pt.up.fe.ddsfl.instrumenter.matchers.MethodAnnotationMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.OrMatcher;
import pt.up.fe.ddsfl.instrumenter.matchers.SuperclassMatcher;

public class TestFilterPass extends FilterPass {

    public TestFilterPass() {
        BlackList junit3 = new BlackList(new SuperclassMatcher("junit.framework.TestCase"));
        BlackList junit4 = new BlackList(new OrMatcher(new MethodAnnotationMatcher("org.junit.Test"),
                new MethodAnnotationMatcher("org.junit.experimental.theories.Theory")));

        add(junit3);
        add(junit4);
    }
}
