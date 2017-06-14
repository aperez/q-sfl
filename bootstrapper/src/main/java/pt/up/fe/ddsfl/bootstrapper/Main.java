package pt.up.fe.ddsfl.bootstrapper;

import java.io.File;
import java.util.Scanner;

import org.junit.runner.JUnitCore;

import pt.up.fe.ddsfl.instrumenter.agent.AgentConfigs;
import pt.up.fe.ddsfl.instrumenter.runtime.Collector;

public class Main {

    public static void main(String...args) {

        String testsFilename = args[0];
        String loadedClassesFilename = args[1];

        try {
            AgentConfigs configs = Collector.instance().getConfigs();
            File file = new File(loadedClassesFilename);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String classToInstrument = sc.nextLine();
                configs.getClassNamesMatcher().add(classToInstrument);
            }
            sc.close();

            JUnitCore junit = new JUnitCore();
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
