package es.dasaur.mvp.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import es.dasaur.mvp.spring.exceptions.MvpInstantiationException;

/**
 * Abstract presenter, linked to autowired model (usually a service) and {@link View}
 * 
 * @author dasaur
 * 
 * @param <V> {@link View} instance.
 */
public abstract class AbstractPresenter <V extends View<? extends Presenter<V>>>
        implements Presenter<V> {
    
    private static final String PRESENTER_FIELD_NAME = "presenter";

    private Presenter<?> parentPresenter;

    @Inject
    protected V view;

    private Class<?> viewImplClass;
    
    private String title;
    
    /**
     * Completes the Presenter-View bidirectional link. <br>
     * This allows for MVP implementations using prototype scoped beans.
     * @throws IllegalArgumentException 
     */
    @PostConstruct
    public final void postConstruct() throws IllegalAccessException {
        this.viewImplClass = view.getClass();
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
                        MvpInstantiationException.ERROR_NO_ACCESS_FORMAT,
                        viewImplClass.getSimpleName(), PRESENTER_FIELD_NAME);
                throw new MvpInstantiationException(message);
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
                    MvpInstantiationException.ERROR_NO_FIELD_FORMAT,
                    viewImplClass.getSimpleName(), presenterName,
                    PRESENTER_FIELD_NAME);
            throw new MvpInstantiationException(message);
        }
        return f;
    }

    @Override
    public void init(){
        view.init();
    }
    
    @Override
    public void refresh(){
        // no action
    }
    
    @Override
    public void close(){
        // no action
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
    public Presenter<?> getParentPresenter() {
        return parentPresenter;
    }

    @Override
    public void setParentPresenter(Presenter<?> parentPresenter) {
        this.parentPresenter = parentPresenter;
    }
    
}
