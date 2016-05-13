package es.dasaur.proto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

import es.dasaur.mvp.spring.Presenter;
import es.dasaur.mvp.spring.View;
import es.dasaur.mvp.spring.exceptions.MvpInstantiationException;

public abstract class ProtoPresenter<V extends View<? extends Presenter<V>>> 
        implements Presenter<V> {
    
    private static final String PRESENTER_FIELD_NAME = "presenter";
    
    protected V view;
    private Class<V> viewClass;
    
    @Autowired 
    private AutowireCapableBeanFactory injector;
    
    @Autowired
    private DefaultListableBeanFactory beanList;
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public final void postConstruct() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(view == null) {
            this.viewClass = (Class<V>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            view =  getImpl().newInstance();
            Field f = getPresenterField();
            f.setAccessible(true);
            f.set(view, this);
            injector.autowireBean(view);
            init();
        }
    }
    
    @SuppressWarnings("unchecked")
    private Class<V> getImpl() throws ClassNotFoundException {
        return (Class<V>) Class.forName(beanList.getBeanDefinition(
                beanList.getBeanNamesForType(viewClass)[0])
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

}
