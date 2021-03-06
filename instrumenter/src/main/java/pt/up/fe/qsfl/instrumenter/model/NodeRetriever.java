package pt.up.fe.qsfl.instrumenter.model;

import java.util.StringTokenizer;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.Descriptor;
import pt.up.fe.qsfl.common.model.Node;
import pt.up.fe.qsfl.instrumenter.runtime.Collector;

public class NodeRetriever {

    private static Node getNode(Collector c, Node parent, String name, Node.Type type, int line) {
        Node node = parent.getChild(name);

        if (node == null) {
            node = c.createNode(parent, name, type, line);
        }

        return node;
    }

    public static Node getNode(CtClass cls) {
        Collector c = Collector.instance();
        Node node = c.getRootNode();
        String tok = cls.getName();

        // Extract Package Hierarchy
        int pkgEnd = tok.lastIndexOf(".");

        if (pkgEnd >= 0) {
            StringTokenizer stok = new StringTokenizer(tok.substring(0, pkgEnd), ".");

            while (stok.hasMoreTokens()) {
                node = getNode(c, node, stok.nextToken(), Node.Type.PACKAGE, -1);
            }
        } else {
            pkgEnd = -1;
        }

        // Extract Class Hierarchy
        StringTokenizer stok = new StringTokenizer(tok.substring(pkgEnd + 1), "$");

        while (stok.hasMoreTokens()) {
            tok = stok.nextToken();
            node = getNode(c, node, tok, Node.Type.CLASS, -1);
        }

        return node;
    }

    public static Node getNode(CtClass cls, CtBehavior m) {
        Collector c = Collector.instance();
        Node parent = getNode(cls);

        return getNode(c, parent, getMethodName(m) + Descriptor.toString(m.getSignature()), Node.Type.METHOD, -1);
    }

    public static Node getNode(CtClass cls, CtBehavior m, int line) {
        Collector c = Collector.instance();
        Node parent = getNode(cls, m);

        return getNode(c, parent, String.valueOf(line), Node.Type.LINE, line);
    }

    public static Node getMethodNode(CtClass cls, CtBehavior m, int line) {
        Collector c = Collector.instance();
        Node parent = getNode(cls);

        return getNode(c, parent, getMethodName(m) + Descriptor.toString(m.getSignature()), Node.Type.METHOD, line);
    }

    private static String getMethodName(CtBehavior m) {
        String name = m.getName();

        int idx = name.lastIndexOf('$');
        if (idx != -1) {
            name = name.substring(idx+1);
        }

        return name;
    }
}
