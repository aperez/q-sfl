package pt.up.fe.qsfl.common.spectrum;

import java.util.BitSet;
import java.util.List;

import pt.up.fe.qsfl.common.model.Node;
import pt.up.fe.qsfl.common.model.Tree;

public interface Spectrum {

    int getComponentsSize();

    int getTransactionsSize();

    boolean isInvolved(int t, int c);

    boolean isError(int t);

    Tree getTree();

    void print();

    BitSet getTransactionActivity(int t);

    String getTransactionName(int t);

    List<Integer> getActiveComponentsInTransaction(int t);

    Node getNodeOfProbe(int probeId);

    int getProbeOfNode(int nodeId);
}
