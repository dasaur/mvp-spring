package es.dasaur.mvp.spring;

/**
 * Sample generic implementation for {@link View}, linked to a {@link Presenter}<br>
 * 
 * @author dasaur
 * 
 * @param <P> {@link Presenter} instance.
 */
public abstract class AbstractView 
        <V extends View<V, P>, P extends Presenter<?, V, P>>
        implements View <V, P> {
    
    protected P presenter;

}
