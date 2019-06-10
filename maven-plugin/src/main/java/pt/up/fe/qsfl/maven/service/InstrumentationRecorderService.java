package pt.up.fe.qsfl.maven.service;

import pt.up.fe.qsfl.common.events.EventListener;
import pt.up.fe.qsfl.common.events.MultiEventListener;
import pt.up.fe.qsfl.common.messaging.Service;
import pt.up.fe.qsfl.common.spectrum.NodeRecorder;
import pt.up.fe.qsfl.common.spectrum.ProbeRecorder;
import pt.up.fe.qsfl.common.spectrum.TransactionRecorder;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeRecorder;

public class InstrumentationRecorderService implements Service {

	public static InstrumentationRecorderService singleton = null;

	public static InstrumentationRecorderService getService(String path) {
		if (singleton == null) {
			singleton = new InstrumentationRecorderService(path);
		}
		return singleton;
	}

	private MultiEventListener listener = null;
	private boolean finished = false;

	public InstrumentationRecorderService(String path) {
		listener = new MultiEventListener();
		//listener.add(new VerboseEventListener());
		listener.add(new NodeRecorder(path + "nodes.txt"));
		listener.add(new ProbeRecorder(path + "probes.txt"));
		listener.add(new TransactionRecorder(path + "transactions.txt"));
		listener.add(new ValueProbeRecorder(path + "valueprobes.txt"));
	}

	public boolean isFinished() {
		return finished;
	}

	@Override
	public EventListener getEventListener() {
		return listener;
	}

	@Override
	public void interrupted() {
		finished = true;
	}

	@Override
	public void terminated() {
		finished = true;
	}

}
