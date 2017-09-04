package pt.up.fe.qsfl.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import pt.up.fe.qsfl.annotations.handlers.LandmarkHandler;
import pt.up.fe.qsfl.annotations.handlers.ObjectHandler;

@Retention(RUNTIME)
@Target({ METHOD, PARAMETER, CONSTRUCTOR })
public @interface ObjectChecker {
    Class<? extends Annotation> type() default LandmarkAnnotation.class;
    Class<? extends LandmarkHandler> handler() default ObjectHandler.class;
}
