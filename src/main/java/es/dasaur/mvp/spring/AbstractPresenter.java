package es.dasaur.mvp.spring;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

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

    
    private String title;
    private Presenter<?> parentPresenter;
    private List<Presenter<?>> children;
    
    protected V view;
    private Class<? extends V> viewClass;
    
    private static final String PRESENTER_FIELD_NAME = "presenter";
    
    @Autowired
    private DefaultListableBeanFactory beanFactory;
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public final void postConstruct() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(view == null) {
            viewClass = (Class<V>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            view =  getImpl().newInstance();
            viewClass = (Class<? extends V>) view.getClass();
            Field f = getPresenterField();
            f.setAccessible(true);
            f.set(view, this);
            beanFactory.autowireBean(view);
            init();
        }
    }
    
    @SuppressWarnings("unchecked")
    private Class<? extends V> getImpl() throws ClassNotFoundException {
        return (Class<? extends V>) Class.forName(beanFactory.getBeanDefinition(
                beanFactory.getBeanNamesForType(viewClass)[0])
                .getBeanClassName());
    }
    

    private Field getPresenterField() {
        Field f;
        try {
            f = viewClass.getDeclaredField(PRESENTER_FIELD_NAME);
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(viewClass.getSuperclass());
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
                        viewClass.getSimpleName(), PRESENTER_FIELD_NAME);
                throw new MvpInstantiationException(message);
            }
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(superclass.getSuperclass());
        } catch (NullPointerException e) {
         // Wraps the exception, further detailing the reason behind it
            ParameterizedType pt = (ParameterizedType)
                    viewClass.getGenericSuperclass();
            Class<?> presenterInterface = (Class<?>) pt.getActualTypeArguments()[0];
            String presenterName = presenterInterface.getSimpleName();
            String message = String.format(
                    MvpInstantiationException.ERROR_NO_FIELD_FORMAT,
                    viewClass.getSimpleName(), presenterName,
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
