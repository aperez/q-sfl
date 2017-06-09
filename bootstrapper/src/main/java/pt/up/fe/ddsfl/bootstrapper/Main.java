package pt.up.fe.ddsfl.bootstrapper;

import java.io.File;
import java.util.Scanner;

import org.junit.runner.JUnitCore;

public class Main {

    public static void main(String...args) {

        String testsFilename = args[0];

        try {
            JUnitCore junit = new JUnitCore();
            File file = new File(testsFilename);
            Scanner sc = new Scanner(file);
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
