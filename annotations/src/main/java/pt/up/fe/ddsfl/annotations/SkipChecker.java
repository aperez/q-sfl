package pt.up.fe.ddsfl.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import pt.up.fe.ddsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.ddsfl.annotations.handlers.SkipHandler;

@Retention(RUNTIME)
@Target({ METHOD, PARAMETER, CONSTRUCTOR })
public @interface SkipChecker {
    Class<? extends Annotation> type() default LandmarkAnnotation.class;
    Class<? extends LandmarkHandler> handler() default SkipHandler.class;
}
