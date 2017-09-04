package pt.up.fe.qsfl.annotations.handlers;

public class ObjectHandler implements LandmarkHandler {

    @Override
    public int handle(Object o) {
        return o == null ? 0 : 1;
    }

    @Override
    public int landmarks() {
        return 2;
    }

    @Override
    public String getLandmarkName(int l) {
        if (l == 0) {
            return "IsNull";
        }
        return "NotNull";
    }

}
