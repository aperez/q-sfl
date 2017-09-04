package pt.up.fe.qsfl.bootstrapper;

import java.io.File;
import java.util.Scanner;

import org.junit.runner.JUnitCore;

import pt.up.fe.qsfl.common.spectrum.NodeRecorder;
import pt.up.fe.qsfl.common.spectrum.ProbeRecorder;
import pt.up.fe.qsfl.common.spectrum.TransactionRecorder;
import pt.up.fe.qsfl.instrumenter.agent.AgentConfigs;
import pt.up.fe.qsfl.instrumenter.runtime.Collector;
import pt.up.fe.qsfl.instrumenter.runtime.TestListener;
import pt.up.fe.qsfl.instrumenter.runtime.data.ValueProbeRecorder;

public class Main {

    public static void main(String...args) {

        String testsFilename = args[0];
        String loadedClassesFilename = args[1];

        try {
            Collector c = Collector.instance();
            c.addListener(new NodeRecorder("nodes.txt"));
            c.addListener(new ProbeRecorder("probes.txt"));
            c.addListener(new TransactionRecorder("transactions.txt"));
            c.addListener(new ValueProbeRecorder("valueprobes.txt"));
            AgentConfigs configs = c.getConfigs();

            File file = new File(loadedClassesFilename);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String classToInstrument = sc.nextLine();
                configs.getClassNamesMatcher().add(classToInstrument);
            }
            sc.close();

            JUnitCore junit = new JUnitCore();
            junit.addListener(new TestListener(false));

            file = new File(testsFilename);
            sc = new Scanner(file);
            while(sc.hasNextLine()){
                String testToExecute = sc.nextLine();
                Class<?> testClass = ClassLoader.getSystemClassLoader().loadClass(testToExecute);
                junit.run(testClass);
            }
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.exit(0);
    }

}
