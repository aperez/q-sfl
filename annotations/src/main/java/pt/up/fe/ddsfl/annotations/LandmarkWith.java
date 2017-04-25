package pt.up.fe.ddsfl.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import pt.up.fe.ddsfl.annotations.dispatchers.Dispatcher;

@Retention(RUNTIME)
@Target(TYPE)
public @interface LandmarkWith {
    Class<? extends Dispatcher> value();
}
