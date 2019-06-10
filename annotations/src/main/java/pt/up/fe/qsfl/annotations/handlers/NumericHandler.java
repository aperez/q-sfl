package pt.up.fe.qsfl.annotations.handlers;

public abstract class NumericHandler implements LandmarkHandler {

    @Override
    public int handle(Object o) {
        return 1 + Integer.signum(compareWithZero(o));
    }

    protected abstract int compareWithZero(Object o);

    @Override
    public int landmarks() {
        return 3;
    }

    @Override
    public String getLandmarkName(int l) {
        if (l == 0) {
            return "lt 0";
        } else if (l == 1) {
            return "eq 0";
        }
        return "gt 0";
    }
}
