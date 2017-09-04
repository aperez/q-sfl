package pt.up.fe.qsfl.bootstrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import pt.up.fe.qsfl.common.model.Node;
import pt.up.fe.qsfl.common.model.Tree;
import pt.up.fe.qsfl.common.spectrum.NodeReader;
import pt.up.fe.qsfl.common.spectrum.SpectrumBuilder;

public class FaultLocation {

    public static void main(String...args) {
        String path = args[0];
        String buggyLinesPath = args[1];

        SpectrumBuilder builder = new SpectrumBuilder();

        NodeReader nr = new NodeReader(path + "/nodes.txt");
        nr.read(builder);
        nr.close();

        Tree tree = builder.getSpectrum().getTree();
        List<Node> classNodes = tree.getNodesOfType(Node.Type.CLASS);

        try {
            FileReader fr = new FileReader(buggyLinesPath);
            BufferedReader br = new BufferedReader(fr);

            Set<Integer> faultyNodes = new TreeSet<Integer>();

            String line;
            while ((line = br.readLine()) != null) {

                String[] partitions = line.split("#");
                if (partitions.length < 2) continue;

                String className = partitions[0];
                if (className.endsWith(".java")) className = className.substring(0, className.length() - 5);
                className = className.replaceAll("/", ".");

                int lineNumber = Integer.parseInt(partitions[1]);

                for (Node node : classNodes) {
                    if (className.equals(node.getFullName())) {
                        Node bestNode = null;

                        for (Node child : node.getChildren()) {
                            if(child.getType() == Node.Type.METHOD && child.getLine() <= lineNumber) {
                                if (bestNode == null || child.getLine() > bestNode.getLine()) {
                                    bestNode = child;
                                } 
                            }
                        }

                        if (bestNode != null) {
                            faultyNodes.add(bestNode.getId());
                        }
                        break;
                    }
                }
            }
            br.close();
            fr.close();

            FileWriter fw = new FileWriter(path + "/faults.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            for (int nodeId : faultyNodes) {
                bw.write(nodeId + "\n");
            }

            bw.close();
            fw.close();

        } catch (IOException e) {
        }

    }

}
