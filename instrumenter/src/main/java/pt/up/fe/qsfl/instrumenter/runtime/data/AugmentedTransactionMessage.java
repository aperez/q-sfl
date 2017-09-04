package pt.up.fe.qsfl.instrumenter.runtime.data;

import pt.up.fe.qsfl.common.messaging.Message;

public class AugmentedTransactionMessage implements Message {
    private static final long serialVersionUID = -2115132392186443580L;

    public String transactionName;
    public int[] landmarks;

    public AugmentedTransactionMessage() {
    }

    public AugmentedTransactionMessage(String transactionName, int[] landmarks) {
        this.transactionName = transactionName;
        this.landmarks = landmarks;
    }
}