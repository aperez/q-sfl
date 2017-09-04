package pt.up.fe.qsfl.instrumenter.runtime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.up.fe.qsfl.annotations.handlers.LandmarkHandler;

public class LandmarkVector {
    private Map<String, LandmarkHandler[]> landmarks = new HashMap<String, LandmarkHandler[]>();

    public LandmarkHandler[] getLandmarkVector(String className) {
        if (!landmarks.containsKey(className)) {
            return new LandmarkHandler[0];
        }
        return landmarks.get(className);
    }

    public void addLandmarkVector(String className, List<LandmarkHandler> landmarksList) {
        landmarks.put(className, landmarksList.toArray(new LandmarkHandler[landmarksList.size()]));
    }
}
