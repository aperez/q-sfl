package pt.up.fe.ddsfl.common.messaging;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

import flexjson.JSONDeserializer;
import flexjson.ObjectBinder;
import flexjson.ObjectFactory;
import pt.up.fe.ddsfl.common.events.EventListener;

public abstract class AbstractMessageReader<M> implements MessageReader {

    private BufferedReader br = null;
    private FileReader fr = null;
    protected JSONDeserializer<M> deserializer = new JSONDeserializer<M>();
    protected Class<M> type;

    public AbstractMessageReader(String filename, Class<M> type) {
        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
        } catch (Exception e) {
            fr = null;
            br = null;
        }
        this.type = type;
    }

    protected void transformBooleans() {
        deserializer = deserializer.use(Boolean.class, new ObjectFactory() {

            public Object instantiate(ObjectBinder arg0, Object arg1, Type arg2, Class arg3) {
                if (arg1 instanceof Number) {
                    return ((Number)arg1).intValue() != 0;
                }
                return Boolean.FALSE;
            }
        });
    }

    public void read(EventListener listener) {
        if (br != null) {
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    M object = deserializer.deserialize(line, type);
                    dispatch(listener, object);
                }
            } catch (IOException e) {
            }
        }
    }

    protected abstract void dispatch(EventListener listener, M object);

    public void close() {
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
            }
        }
        br = null;

        if (fr != null) {
            try {
                fr.close();
            } catch (IOException e) {
            }
        }
        fr = null;
    }
}
