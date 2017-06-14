package pt.up.fe.ddsfl.common.messaging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import flexjson.JSONSerializer;

public class MessageRecorder {

    private BufferedWriter bw = null;
    private FileWriter fw = null;
    protected JSONSerializer serializer = new JSONSerializer();


    public MessageRecorder(String filename, boolean excludeClass) {
        try {
            fw = new FileWriter(filename);
            bw = new BufferedWriter(fw);
        } catch (Exception e) {
            fw = null;
            bw = null;
        }
        
        if (excludeClass) {
            serializer = serializer.exclude("*.class");
        }
    }

    public void writeMessage(Message message) {
        if (bw != null) {
            try {
                bw.write(serializer.deepSerialize(message));
                bw.newLine();
            } catch (IOException e) {
            }
        }
    }

    public void close() {
        if  (bw != null) {
            try {
                bw.close();
            } catch (IOException e) {
            }
        }
        bw = null;

        if  (fw != null) {
            try {
                fw.close();
            } catch (IOException e) {
            }
        }
        fw = null;
    }

}
