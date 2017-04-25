package pt.up.fe.ddsfl.annotations.handlers;

public abstract class NumericHandler implements LandmarkHandler {

    @Override
    public int landmarks() {
        return 3;
    }

    @Override
    public String getLandmarkName(int l) {
        if (l == 0) {
            return "<0";
        }
        else if (l == 1) {
            return "=0";
        }
        return ">0";
    }
}