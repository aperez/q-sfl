package pt.up.fe.ddsfl.instrumenter.runtime.data;

import pt.up.fe.ddsfl.common.events.EventListener;
import pt.up.fe.ddsfl.common.messaging.AbstractMessageReader;
import pt.up.fe.ddsfl.common.spectrum.SpectrumBuilder;

public class AugmentedTransactionReader extends AbstractMessageReader<AugmentedTransactionMessage> {

    public AugmentedTransactionReader(String filename) {
        super(filename, AugmentedTransactionMessage.class);
    }

    @Override
    protected void dispatch(EventListener listener, AugmentedTransactionMessage object) {
        if (listener instanceof SpectrumBuilder) {
            SpectrumBuilder sb = (SpectrumBuilder) listener;

            if (object.landmarks == null) return;
            for (int i = 0; i < object.landmarks.length; ++i) {
                object.landmarks[i] = sb.getProbeOfNode(object.landmarks[i]);
            }
            sb.addTransactionHits(object.transactionName, object.landmarks);
        }
    }

}
