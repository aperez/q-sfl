package pt.up.fe.ddsfl.instrumenter.passes;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.common.model.Node.Type;
import pt.up.fe.ddsfl.instrumenter.model.NodeRetriever;
import pt.up.fe.ddsfl.instrumenter.runtime.Collector;

public class ParameterDataPass implements Pass {

    @Override
    public Outcome transform(CtClass c) throws Exception {

        for (CtBehavior b : c.getDeclaredBehaviors()) {
            try {
                handleBehavior(c, b);
            } catch (Exception e) {
            }
        }

        return Outcome.CONTINUE;
    }

    private void handleBehavior(CtClass c, CtBehavior b) throws Exception {
        if(InstrumentationPass.toSkip(c, b)) {
            return;
        }

        boolean skipMemberParameter =  b instanceof CtConstructor &&
                c.getDeclaringClass() != null &&
                !Modifier.isStatic(c.getModifiers());

        MethodInfo info = b.getMethodInfo();
        CodeAttribute ca = info.getCodeAttribute();

        Node n = NodeRetriever.getNode(c, b);
        Collector collector = Collector.instance();

        LocalVariableAttribute attr = (LocalVariableAttribute) ca.getAttribute(LocalVariableAttribute.tag);
        CtClass[] params = b.getParameterTypes();
        int pos = (Modifier.isStatic(b.getModifiers()) || skipMemberParameter) ? 0 : 1;

        int i = skipMemberParameter ? 1 : 0;
        for (; i < params.length; i++) {
            String parameterName = "parameter#"+i;
            if (attr != null && attr.tableLength() > i + pos) {
                parameterName = attr.variableName(i + pos);
            }
            Node parameterNode = collector.createNode(n, parameterName, Type.PARAMETER, n.getLine());
            //insert "$args[" + i + "]" data probe (parameterNode node)
        }

        if (b instanceof CtMethod) {
            CtClass returnType = ((CtMethod) b).getReturnType();
            if (returnType != CtClass.voidType) {
                //insert "($w)$_" data probe (n node)
            }
        }
    }

}
