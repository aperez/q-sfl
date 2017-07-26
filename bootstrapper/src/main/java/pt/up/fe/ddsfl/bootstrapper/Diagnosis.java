package pt.up.fe.ddsfl.bootstrapper;

import java.util.ArrayList;
import java.util.List;

import pt.up.fe.ddsfl.common.messaging.MessageReader;
import pt.up.fe.ddsfl.common.spectrum.NodeReader;
import pt.up.fe.ddsfl.common.spectrum.ProbeReader;
import pt.up.fe.ddsfl.common.spectrum.Spectrum;
import pt.up.fe.ddsfl.common.spectrum.SpectrumBuilder;
import pt.up.fe.ddsfl.common.spectrum.SpectrumFilter;
import pt.up.fe.ddsfl.common.spectrum.SpectrumImpl;
import pt.up.fe.ddsfl.common.spectrum.TransactionReader;
import pt.up.fe.ddsfl.diagnoser.barinel.Barinel;
import pt.up.fe.ddsfl.diagnoser.barinel.Barinel.Result;
import pt.up.fe.ddsfl.diagnoser.barinel.BarinelRecorder;
import pt.up.fe.ddsfl.diagnoser.barinel.MHS;
import pt.up.fe.ddsfl.diagnoser.trie.Trie;

public class Diagnosis {

    public static void main(String... args) {
        String path = args[0];

        SpectrumBuilder builder = new SpectrumBuilder();

        List<MessageReader> readers = new ArrayList<MessageReader>();
        readers.add(new NodeReader(path + "/nodes.txt"));
        //readers.add(new NodeReader(path + "/landmarks.default.txt"));
        readers.add(new ProbeReader(path + "/probes.txt"));
        readers.add(new TransactionReader(path + "/transactions.txt"));
        //readers.add(new AugmentedTransactionReader(path + "/transactions.default.txt"));

        for (MessageReader reader : readers) {
            reader.read(builder);
            reader.close();
        }

        Spectrum spectrum = builder.getSpectrum();
        if (spectrum instanceof SpectrumImpl) {
            SpectrumFilter sf = new SpectrumFilter(spectrum);
            Trie trie = new MHS().generate(sf);

            List<Result> results = new Barinel().calculate(sf, trie);
            BarinelRecorder br = new BarinelRecorder(path + "/barinel.txt");
            br.write(spectrum, results);
            br.close();
        }
    }

}
