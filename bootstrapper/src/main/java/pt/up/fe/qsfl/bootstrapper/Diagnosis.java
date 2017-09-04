package pt.up.fe.qsfl.bootstrapper;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.qsfl.common.messaging.MessageReader;
import pt.up.fe.qsfl.common.spectrum.NodeReader;
import pt.up.fe.qsfl.common.spectrum.ProbeReader;
import pt.up.fe.qsfl.common.spectrum.Spectrum;
import pt.up.fe.qsfl.common.spectrum.SpectrumBuilder;
import pt.up.fe.qsfl.common.spectrum.SpectrumFilter;
import pt.up.fe.qsfl.common.spectrum.SpectrumImpl;
import pt.up.fe.qsfl.common.spectrum.TransactionReader;
import pt.up.fe.qsfl.diagnoser.barinel.Barinel;
import pt.up.fe.qsfl.diagnoser.barinel.BarinelRecorder;
import pt.up.fe.qsfl.diagnoser.barinel.MHS;
import pt.up.fe.qsfl.diagnoser.barinel.Barinel.Result;
import pt.up.fe.qsfl.diagnoser.trie.Trie;
import pt.up.fe.qsfl.instrumenter.runtime.data.AugmentedTransactionReader;

public class Diagnosis {

    public static void main(String... args) {
        String path = args[0];
        String landmarkCriterion = null;
        if (args.length > 1) {
            landmarkCriterion = args[1];
        }

        SpectrumBuilder builder = new SpectrumBuilder();

        List<MessageReader> readers = new ArrayList<MessageReader>();
        readers.add(new NodeReader(path + "/nodes.txt"));
        if (landmarkCriterion != null) {
            readers.add(new NodeReader(path + "/landmarks."+landmarkCriterion+".txt"));
        }
        readers.add(new ProbeReader(path + "/probes.txt"));
        readers.add(new TransactionReader(path + "/transactions.txt"));
        if (landmarkCriterion != null) {
            readers.add(new AugmentedTransactionReader(path + "/transactions."+landmarkCriterion+".txt"));
        }

        for (MessageReader reader : readers) {
            reader.read(builder);
            reader.close();
        }

        Spectrum spectrum = builder.getSpectrum();
        if (spectrum instanceof SpectrumImpl) {
            SpectrumFilter sf = new SpectrumFilter(spectrum);
            Trie trie = new MHS().generate(sf);

            List<Result> results = new Barinel().calculate(sf, trie);
            BarinelRecorder br = null;
            if (landmarkCriterion != null) {
                br = new BarinelRecorder(path + "/barinel."+landmarkCriterion+".txt");
            } else {
                br = new BarinelRecorder(path + "/barinel.txt");
            }
            br.write(spectrum, results);
            br.close();
        }
    }

}
