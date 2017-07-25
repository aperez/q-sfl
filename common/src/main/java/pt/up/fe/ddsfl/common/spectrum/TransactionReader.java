package pt.up.fe.ddsfl.common.spectrum;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.Message.EndTransactionMessage;
import pt.up.fe.ddsfl.common.messaging.MessageReader;

public class TransactionReader extends MessageReader<EndTransactionMessage> {

    public TransactionReader(String filename) {
        super(filename, EndTransactionMessage.class);
        transformBooleans();
    }

    @Override
    protected void dispatch(EventListener listener, EndTransactionMessage object) {
        listener.endTransaction(object.transactionName, object.activity, object.isError);
    }

}
