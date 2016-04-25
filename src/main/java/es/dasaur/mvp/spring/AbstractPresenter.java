package es.dasaur.mvp.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import es.dasaur.mvp.spring.exceptions.ViewInstantiationException;

/**
 * Abstract presenter, linked to autowired model (usually a service) and {@link View}
 * 
 * @author dasaur
 * 
 * @param <M> Model instance, often a service.
 * @param <V> {@link View} instance.
 * @param <P> {@link Presenter} instance.
 */
public abstract class AbstractPresenter 
        <M, V extends View<V, P>, P extends Presenter<M, V, P>>
        implements Presenter<M, V, P> {
    
    private static final String PRESENTER_FIELD_NAME = "presenter";

    private Presenter<?, ?, ?> parentPresenter;
    
    @Inject
    protected M service;
    
    @Inject
    protected V view;

    private Class<? extends V> viewImplClass;
    
    private String title;
    
    /**
     * Constructor getting the view class for setting the Presenter-View 
     * bidirectional link.
     * @param viewImplClass
     */
    protected AbstractPresenter(Class<? extends V> viewImplClass){
        this.viewImplClass = viewImplClass;
    }
    
    /**
     * Completes the Presenter-View bidirectional link. <br>
     * This allows for MVP implementations using prototype scoped beans.
     * @throws IllegalArgumentException 
     */
    @PostConstruct
    public final void postConstruct() throws IllegalAccessException {
        Field f = getPresenterField();
        f.setAccessible(true);
        f.set(view, this);
        init();
    }
    
    private Field getPresenterField() {
        Field f;
        try {
            f = viewImplClass.getDeclaredField(PRESENTER_FIELD_NAME);
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(viewImplClass.getSuperclass());
        }
        return f;
    }

    private Field getInheritedPresenterField(Class<?> superclass) {
        Field f;
        try {
            f = superclass.getDeclaredField(PRESENTER_FIELD_NAME);
            int mod = f.getModifiers();
            if (!Modifier.isPublic(mod) && !Modifier.isProtected(mod)){
             // Wraps the exception, further detailing the reason behind it
                String message = String.format(
                        ViewInstantiationException.ERROR_NO_ACCESS_FORMAT,
                        viewImplClass.getSimpleName(), PRESENTER_FIELD_NAME);
                throw new ViewInstantiationException(message);
            }
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(superclass.getSuperclass());
        } catch (NullPointerException e) {
         // Wraps the exception, further detailing the reason behind it
            ParameterizedType pt = (ParameterizedType)
                    viewImplClass.getGenericSuperclass();
            Class<?> presenterInterface = (Class<?>) pt.getActualTypeArguments()[0];
            String presenterName = presenterInterface.getSimpleName();
            String message = String.format(
                    ViewInstantiationException.ERROR_NO_FIELD_FORMAT,
                    viewImplClass.getSimpleName(), presenterName,
                    PRESENTER_FIELD_NAME);
            throw new ViewInstantiationException(message);
        }
        return f;
    }

    @Override
    public void init(){
        view.init();
    }
    
    @Override
    public void refresh(){
        view.refresh();
    }
    
    @Override
    public void close(){
        view.close();
    }
    
    @Override
    public final String getTitle() {
        return title;
    }

    @Override
    public final void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Presenter<?, ?, ?> getParentPresenter() {
        return parentPresenter;
    }

    @Override
    public void setParentPresenter(Presenter<?, ?, ?> parentPresenter) {
        this.parentPresenter = parentPresenter;
    }
    
}
