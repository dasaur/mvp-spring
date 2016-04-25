package es.dasaur.mvp.spring;

/**
 * View class.<br>
 * Implementations of this class must declare a P field named "presenter".
 * 
 * @author dasaur
 *
 * @param <P> {@link Presenter} instance.
 */
public interface View 
    <P extends Presenter<?, ? extends View<P>>> {
    
    /**
     * Executes view initialization logic.
     */
    void init();
    
    /**
     * Executes view refreshing logic.
     */
    void refresh();

    /**
     * Executes view finalization logic.
     */
    void close();

}
