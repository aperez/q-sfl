package pt.up.fe.qsfl.instrumenter.agent;

import java.lang.instrument.Instrumentation;

import pt.up.fe.qsfl.instrumenter.passes.ClassTransformer;
import pt.up.fe.qsfl.instrumenter.runtime.Collector;

public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {

        AgentConfigs agentConfigs = AgentConfigs.deserialize(agentArgs);
        if (agentConfigs == null) {
            agentConfigs = new AgentConfigs(); // return;
        }

        Collector.start(agentConfigs);
        ClassTransformer transformer = new ClassTransformer(agentConfigs.getInstrumentationPasses());
        inst.addTransformer(transformer);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                Collector.instance().endSession();
            }
        });
    }

}