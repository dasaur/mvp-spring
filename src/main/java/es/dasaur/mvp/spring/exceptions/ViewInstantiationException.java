package es.dasaur.mvp.spring.exceptions;

import es.dasaur.mvp.spring.View;

/**
 * Exception thrown when trying to instance a {@link View} without a presenter field.
 * 
 * @author dasaur
 *
 */
public class ViewInstantiationException extends RuntimeException {

    private static final long serialVersionUID = 6392164110838064896L;
    
    public static final String ERROR_NO_FIELD_FORMAT = 
            "%s must have the following accesible field (either in the class itself or a superclass): %s %s";

    public static final String ERROR_NO_ACCESS_FORMAT = 
            "%s must have access to the %s field";

    public ViewInstantiationException() {
        super();
    }

    public ViewInstantiationException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ViewInstantiationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewInstantiationException(String message) {
        super(message);
    }

    public ViewInstantiationException(Throwable cause) {
        super(cause);
    }

}
