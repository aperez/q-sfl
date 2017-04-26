package pt.up.fe.ddsfl.instrumenter.passes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import pt.up.fe.ddsfl.annotations.LandmarkAnnotation;
import pt.up.fe.ddsfl.annotations.LandmarkWith;
import pt.up.fe.ddsfl.annotations.dispatchers.DefaultDispatcher;
import pt.up.fe.ddsfl.annotations.dispatchers.Dispatcher;
import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.common.model.Node.Type;
import pt.up.fe.ddsfl.instrumenter.model.NodeRetriever;
import pt.up.fe.ddsfl.instrumenter.runtime.Collector;
import pt.up.fe.ddsfl.instrumenter.runtime.ProbeGroup.HitProbe;

public class LandmarkInserterPass implements Pass {

    public static final String LANDMARK_VECTOR_NAME = "$__DDSFL_LANDMARK_VECTOR__";

    private Dispatcher dispatcher;
    private Dispatcher defaultDispatcher = new DefaultDispatcher();

    @Override
    public Outcome transform(CtClass c) throws Exception {
        try {
            //check if class contains instrumentation field
            c.getDeclaredField(InstrumentationPass.HIT_VECTOR_NAME);
        } catch (Exception e) {
            return Outcome.CONTINUE;
        }

        dispatcher = getLandmarkDispatcher(c);
        if (dispatcher == null) {
            return Outcome.CONTINUE;
        }

        CtField f = CtField.make(
                "private static " + LandmarkHandler.class.getCanonicalName() + "[] " + LANDMARK_VECTOR_NAME + ";", c);
        f.setModifiers(f.getModifiers() | AccessFlag.SYNTHETIC);
        c.addField(f);

        CtConstructor initializer = c.makeClassInitializer();
        initializer.insertBefore(LANDMARK_VECTOR_NAME + " = " + Collector.class.getCanonicalName()
                + ".instance().getLandmarkVector(\"" + c.getName() + "\");");

        List<LandmarkHandler> handlers = new ArrayList<LandmarkHandler>();
        for (CtBehavior b : c.getDeclaredBehaviors()) {
            handleBehavior(c, b, handlers);
        }
        Collector.instance().addLandmarkVector(c.getName(), handlers);

        return Outcome.CONTINUE;
    }

    private Dispatcher getLandmarkDispatcher(CtClass c) {
        Object[] annotations = c.getAvailableAnnotations();

        for (Object o : annotations) {
            if (o instanceof LandmarkWith) {
                try {
                    LandmarkWith lw = (LandmarkWith) o;
                    return lw.value().newInstance();
                } catch (Exception e) {
                }
            }
        }

        return defaultDispatcher;
    }

    private void handleBehavior(CtClass c, CtBehavior b, List<LandmarkHandler> handlers) throws Exception {
        MethodInfo info = b.getMethodInfo();
        CodeAttribute ca = info.getCodeAttribute();

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
        Object[][] paramsAnnotations = b.getAvailableParameterAnnotations();
        int pos = Modifier.isStatic(b.getModifiers()) ? 0 : 1;

        for (int i = 0; i < params.length; i++) {

            String parameterName = attr.variableName(i + pos);
            Node parameterNode = collector.createNode(n, parameterName, Type.PARAMETER, n.getLine());

            LandmarkHandler handler = getLandmarkHandler(params[i], paramsAnnotations[i], false);
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

    private LandmarkHandler getLandmarkHandler(CtClass paramType, Object[] annotations, boolean isReturn) throws NotFoundException {
        if (!dispatcher.overrideAnnotations()) {
            LandmarkHandler handler = processAnnotations(annotations);
            if (handler != null) {
                return handler;
            }
        }
        return dispatcher.getHandler(paramType.getName(), isReturn);
    }

    private LandmarkHandler processAnnotations(Object[] annotations) {
        if (annotations == null) {
            return null;
        }

        for (Object o : annotations) {
            try {
                Method m = o.getClass().getDeclaredMethod("type");
                if(m.invoke(o) != LandmarkAnnotation.class) {
                    continue;
                }
                m = o.getClass().getDeclaredMethod("handler");
                Object ret = m.invoke(o);
                if (ret instanceof Class<?>) {
                    LandmarkHandler handler = (LandmarkHandler)((Class<?>)ret).newInstance();
                    return handler;
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
