package es.dasaur.mvp.spring;

/**
 * Sample generic implementation for {@link View}, linked to a {@link Presenter}<br>
 * 
 * @author dasaur
 * 
 * @param <P> {@link Presenter} instance.
 */
public abstract class AbstractView <P extends Presenter<? extends View<P>>>
        implements View <P> {
    
    protected P presenter;
    
}
