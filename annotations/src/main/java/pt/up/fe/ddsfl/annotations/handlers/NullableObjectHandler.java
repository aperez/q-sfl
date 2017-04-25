package pt.up.fe.ddsfl.annotations.handlers;

public class NullableObjectHandler implements LandmarkHandler {
    private LandmarkHandler delegate;
    private String nullStr = "IsNull";
    
    public NullableObjectHandler(LandmarkHandler delegate) {
        this.delegate = delegate;
    }
    
    public NullableObjectHandler(LandmarkHandler delegate, String nullStr) {
        this.delegate = delegate;
        this.nullStr = nullStr;
    }

    @Override
    public int handle(Object o) {
        if (o == null) {
            return 0;
        }
        return 1 + delegate.handle(o);
    }

    @Override
    public int landmarks() {
        return 1 + delegate.landmarks();
    }

    @Override
    public String getLandmarkName(int l) {
        if (l == 0) {
            return nullStr;
        }
        return delegate.getLandmarkName(l-1);
    }
}
