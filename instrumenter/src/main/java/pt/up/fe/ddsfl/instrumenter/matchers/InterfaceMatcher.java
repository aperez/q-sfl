package pt.up.fe.ddsfl.instrumenter.matchers;

import javassist.CtClass;
import javassist.CtMethod;

public class InterfaceMatcher implements Matcher {
    private final String interfaceName;

    public InterfaceMatcher(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public boolean matches(CtClass c) {
        return matchInterfaces(c);
    }

    @Override
    public boolean matches(CtClass c, CtMethod m) {
        return matchInterfaces(c);
    }

    private boolean matchInterfaces(CtClass c) {
        try {
            for (CtClass i : c.getInterfaces()) {
                if (i.getName().equals(interfaceName)) {
                    return true;
                }
            }
        }
        catch (Exception e) {
        }
        return false;
    }

}
