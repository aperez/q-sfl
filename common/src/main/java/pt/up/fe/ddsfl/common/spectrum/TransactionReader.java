package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.AbstractMessageReader;
import pt.up.fe.ddsfl.common.messaging.Message.EndTransactionMessage;

public class TransactionReader extends AbstractMessageReader<EndTransactionMessage> {

    public TransactionReader(String filename) {
        super(filename, EndTransactionMessage.class);
        transformBooleans();
    }

    @Override
    protected void dispatch(EventListener listener, EndTransactionMessage object) {
        listener.endTransaction(object.transactionName, object.activity, object.isError);
    }

}
