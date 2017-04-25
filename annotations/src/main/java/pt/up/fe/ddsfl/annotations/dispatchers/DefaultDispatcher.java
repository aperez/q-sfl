package pt.up.fe.ddsfl.annotations.dispatchers;

import java.util.HashMap;
import java.util.Map;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.annotations.handlers.ObjectHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.BooleanHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.ByteHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.DoubleHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.FloatHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.IntegerHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.LongHandler;
import pt.up.fe.ddsfl.annotations.handlers.primitives.ShortHandler;

public class DefaultDispatcher implements Dispatcher {
    
    private Map<String, LandmarkHandler> typeHandlers;
    private LandmarkHandler defaultHandler = null;
    
    public DefaultDispatcher() {
        typeHandlers = new HashMap<String, LandmarkHandler>();
        
        typeHandlers.put(byte.class.getName(), new ByteHandler());
        typeHandlers.put(short.class.getName(), new ShortHandler());        
        typeHandlers.put(int.class.getName(), new IntegerHandler());
        typeHandlers.put(long.class.getName(), new LongHandler());
        typeHandlers.put(float.class.getName(), new FloatHandler());
        typeHandlers.put(double.class.getName(), new DoubleHandler());
        typeHandlers.put(boolean.class.getName(), new BooleanHandler());
        
        typeHandlers.put(Byte.class.getName(), new ByteHandler.BoxedHandler());
        typeHandlers.put(Short.class.getName(), new ShortHandler.BoxedHandler());        
        typeHandlers.put(Integer.class.getName(), new IntegerHandler.BoxedHandler());
        typeHandlers.put(Long.class.getName(), new LongHandler.BoxedHandler());
        typeHandlers.put(Float.class.getName(), new FloatHandler.BoxedHandler());
        typeHandlers.put(Double.class.getName(), new DoubleHandler.BoxedHandler());
        typeHandlers.put(Boolean.class.getName(), new BooleanHandler.BoxedHandler());
        
        setDefaultHandler(new ObjectHandler());
    }
    
    @Override
    public void setHandler(String type, LandmarkHandler handler) {
        typeHandlers.put(type, handler);
    }

    @Override
    public void setDefaultHandler(LandmarkHandler handler) {
        defaultHandler = handler;
    }

    @Override
    public LandmarkHandler getHandler(String type) {
        LandmarkHandler handler = typeHandlers.get(type);
        if (handler == null) {
            handler = defaultHandler;
        }
        return handler;
    }
    
}
