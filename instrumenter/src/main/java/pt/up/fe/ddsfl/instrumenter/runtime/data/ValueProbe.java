package pt.up.fe.ddsfl.instrumenter.runtime.data;

public class ValueProbe {
    public static void handle(int node, Object object) {
        System.out.println("node: " + node + " ; object: " + object + " ; " + (object instanceof Object[]));
    }
}
