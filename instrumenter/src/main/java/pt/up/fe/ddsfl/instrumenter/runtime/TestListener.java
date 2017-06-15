package pt.up.fe.ddsfl.instrumenter.runtime;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TestListener extends RunListener {

    private boolean isError = false;
    private boolean sendEndSession = true;

    public TestListener() {
        setSendEndSession(true);
    }

    public TestListener(boolean sendEndSession) {
        setSendEndSession(sendEndSession);
    }

    public void setSendEndSession(boolean sendEndSession) {
        this.sendEndSession = sendEndSession;
    }

    @Override
    public void testRunStarted(Description description) {
        // System.out.println("Test Run Started");
    }

    @Override
    public void testStarted(Description description) {
        // System.out.println("Started " + description.getClassName() + "#" +
        // description.getMethodName());
        this.isError = false;
        Collector.instance().startTransaction();
    }

    @Override
    public void testFinished(Description description) {
        String transactionName = description.getClassName() + "#" + description.getMethodName();
        Collector.instance().endTransaction(transactionName, this.isError);
    }

    @Override
    public void testRunFinished(Result result) {
        if (sendEndSession) {
            Collector.instance().endSession();
        }
    }

    @Override
    public void testAssumptionFailure(Failure failure) {
        this.isError = true;
    }

    @Override
    public void testFailure(Failure failure) {
        this.isError = true;
    }
}
