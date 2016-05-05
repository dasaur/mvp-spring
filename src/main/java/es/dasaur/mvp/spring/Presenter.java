package es.dasaur.mvp.spring;


/**
 * Presenter interface.<br>
 * Implementations of this class are encouraged to extend {@link AbstractPresenter}.
 * 
 * @author dasaur
 *
 * @param <V> {@link View} instance.
 */
public interface Presenter <V extends View<? extends Presenter<V>>> {

    /**
     * Executes presenter initialization logic.
     */
    void init();

    /**
     * Executes presenter refreshing logic.
     */
    void refresh();

    /**
     * Executes presenter finalization logic.
     */
    void close();
    
    /**
     * Gets the presenter title.
     * @return Presenter's title
     */
    String getTitle();
    
    /**
     * Sets the presenter title.
     * @param title New title for the presenter.
     */
    void setTitle(String title);

    /**
     * @return the mainPresenter
     */
    Presenter<?> getParentPresenter();

    /**
     * @param mainPresenter the mainPresenter to set
     */
    void setParentPresenter(Presenter<?> mainPresenter);
}
