package pt.up.fe.qsfl.annotations.handlers;

public class SkipHandler implements LandmarkHandler {

    @Override
    public int handle(Object o) {
        return 0;
    }

    @Override
    public int landmarks() {
        return 0;
    }

    @Override
    public String getLandmarkName(int l) {
        return null;
    }

}
