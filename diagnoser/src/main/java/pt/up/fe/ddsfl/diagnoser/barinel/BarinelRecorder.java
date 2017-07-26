package pt.up.fe.ddsfl.diagnoser.barinel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pt.up.fe.ddsfl.common.messaging.Message;
import pt.up.fe.ddsfl.common.messaging.MessageRecorder;
import pt.up.fe.ddsfl.common.model.Node;
import pt.up.fe.ddsfl.common.spectrum.Spectrum;
import pt.up.fe.ddsfl.diagnoser.barinel.Barinel.Result;

public class BarinelRecorder extends MessageRecorder {

    private static final Node.Type typeTarget = Node.Type.METHOD;

    public static class BarinelResultMessage implements Message {

        private static final long serialVersionUID = 1633400454269285302L;

        public int nodeId;
        public double score;

        public BarinelResultMessage(int nodeId, double score) {
            this.nodeId = nodeId;
            this.score = score;
        }

        protected BarinelResultMessage() {
            this(-1, 0.0);
        }
    }

    public BarinelRecorder(String filename) {
        super(filename, true);
    }

    public void write(Spectrum spectrum, List<Result> results) {
        Map<Integer, Double> reducedScores = new HashMap<Integer, Double>();
        List<BarinelResultMessage> messages = new ArrayList<BarinelResultMessage>();

        for (Result result : results) {
            System.out.println(result);
            for (Integer probe : result.candidate) {
                Node node = spectrum.getNodeOfProbe(probe);

                while (node != null) {
                    if (node.getType() != typeTarget) {
                        node = node.getParent();
                    }
                    else {
                        break;
                    }
                }
                if (node == null) continue;
                int nodeId = node.getId();

                if (reducedScores.containsKey(nodeId)) {
                    reducedScores.put(nodeId, reduce(reducedScores.get(nodeId), result.probability));
                } else {
                    reducedScores.put(nodeId, result.probability);
                }
            }
        }

        for (Entry<Integer, Double> entry : reducedScores.entrySet()) {
            messages.add(new BarinelResultMessage(entry.getKey(), entry.getValue()));
        }

        messages.sort(new Comparator<BarinelResultMessage>() {
            @Override
            public int compare(BarinelResultMessage o1, BarinelResultMessage o2) {
                return Double.compare(o2.score, o1.score);
            }
        });

        for (BarinelResultMessage m : messages) {
            writeMessage(m);
        }
    }

    private double reduce(double oldValue, double newValue) {
        return oldValue + newValue;
    }
}
