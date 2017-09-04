package pt.up.fe.qsfl.instrumenter.passes;

import pt.up.fe.qsfl.instrumenter.matchers.BlackList;
import pt.up.fe.qsfl.instrumenter.matchers.MethodAnnotationMatcher;
import pt.up.fe.qsfl.instrumenter.matchers.OrMatcher;
import pt.up.fe.qsfl.instrumenter.matchers.SuperclassMatcher;

public class TestFilterPass extends FilterPass {

    public TestFilterPass() {
        BlackList junit3 = new BlackList(new SuperclassMatcher("junit.framework.TestCase"));
        BlackList junit4 = new BlackList(new OrMatcher(new MethodAnnotationMatcher("org.junit.Test"),
                new MethodAnnotationMatcher("org.junit.experimental.theories.Theory")));

        add(junit3);
        add(junit4);
    }
}
