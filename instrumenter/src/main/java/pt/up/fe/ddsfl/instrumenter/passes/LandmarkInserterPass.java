package pt.up.fe.ddsfl.instrumenter.passes;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.Modifier;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.annotations.handlers.ObjectHandler;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.common.model.Node.Type;
import pt.up.fe.ddsfl.instrumenter.model.NodeRetriever;
import pt.up.fe.ddsfl.instrumenter.runtime.Collector;
import pt.up.fe.ddsfl.instrumenter.runtime.ProbeGroup.HitProbe;

public class LandmarkInserterPass implements Pass {

    public static final String LANDMARK_VECTOR_NAME = "$__DDSFL_LANDMARK_VECTOR__";

    @Override
    public Outcome transform(CtClass c) throws Exception {
        //check if class contains instrumentation field

        List<LandmarkHandler> handlers = new ArrayList<LandmarkHandler>();

        CtField f = CtField.make(
                "private static " + LandmarkHandler.class.getCanonicalName() + "[] " + LANDMARK_VECTOR_NAME + ";", c);
        f.setModifiers(f.getModifiers() | AccessFlag.SYNTHETIC);
        c.addField(f);

        for (CtBehavior b : c.getDeclaredBehaviors()) {
            handleBehavior(c, b, handlers);
        }
        Collector.instance().addLandmarkVector(c.getName(), handlers);

        CtConstructor initializer = c.makeClassInitializer();
        initializer.insertBefore(LANDMARK_VECTOR_NAME + " = " + Collector.class.getCanonicalName()
                + ".instance().getLandmarkVector(\"" + c.getName() + "\");");

        return Outcome.CONTINUE;
    }

    private void handleBehavior(CtClass c, CtBehavior b, List<LandmarkHandler> handlers) throws Exception {
        MethodInfo info = b.getMethodInfo();
        CodeAttribute ca = info.getCodeAttribute();

        // skip synthetic methods
        if ((b.getModifiers() & AccessFlag.SYNTHETIC) != 0) {
            return;
        }

        if (ca != null) {
            if (b instanceof CtConstructor) {
                if (((CtConstructor) b).isClassInitializer()) {
                    return;
                }
            }
        }

        Node n = NodeRetriever.getNode(c, b);
        Collector collector = Collector.instance();

        LocalVariableAttribute attr = (LocalVariableAttribute) ca.getAttribute(LocalVariableAttribute.tag);
        CtClass[] params = b.getParameterTypes();
        int pos = Modifier.isStatic(b.getModifiers()) ? 0 : 1;

        for (int i = 0; i < params.length; i++) {

            String parameterName = attr.variableName(i + pos);
            Node parameterNode = collector.createNode(n, parameterName, Type.PARAMETER, n.getLine());

            LandmarkHandler handler = getLandmarkHandler(b, i);
            if (handler == null || handler.landmarks() < 2) {
                continue;
            }

            insertLandmark(c, b, parameterNode, handlers, "$args[" + i + "]", handler);
        }

        //return value landmarks
    }

    private void insertLandmark(CtClass c, CtBehavior b, Node node, List<LandmarkHandler> handlers, String variable,
            LandmarkHandler handler) throws CannotCompileException {
        Collector collector = Collector.instance();

        int offset = 0;

        for (int l = 0; l < handler.landmarks(); l++) {
            Node landmarkNode = collector.createNode(node, handler.getLandmarkName(l), Type.LANDMARK, node.getLine());
            HitProbe probe = collector.createHitProbe(c.getName(), landmarkNode.getId());

            if (l == 0) {
                offset = probe.getLocalId();
            }
        }

        int landmark = handlers.size();
        handlers.add(handler);
        b.insertBefore("{ " + InstrumentationPass.HIT_VECTOR_NAME + "[" + offset + "+" + LANDMARK_VECTOR_NAME + "["
                + landmark + "].handle(" + variable + ")" + "] = true; }");
    }

    private LandmarkHandler getLandmarkHandler(CtBehavior b, int i) {
        return new ObjectHandler();
    }

}
