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

public abstract class ProtoView<P extends Presenter<? extends View<P>>> 
        implements View<P> {
    
    private static final String VIEW_FIELD_NAME = "view";
    protected P presenter;
    private Class<P> presenterClass;
    
    @Autowired 
    private AutowireCapableBeanFactory injector;
    
    @Autowired
    private DefaultListableBeanFactory context;
    
    @SuppressWarnings("unchecked")
    @PostConstruct
    public final void postConstruct() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if(presenter == null) {
            this.presenterClass = (Class<P>) ((ParameterizedType) getClass()
                    .getGenericSuperclass()).getActualTypeArguments()[0];
            presenter = getImpl().newInstance();
            Field f = getPresenterField();
            f.setAccessible(true);
            f.set(presenter, this);
            injector.autowireBean(presenter);
            init();
        }
    }
    
    @SuppressWarnings("unchecked")
    private Class<P> getImpl() throws ClassNotFoundException {
        return (Class<P>) Class.forName(context.getBeanDefinition(
                context.getBeanNamesForType(presenterClass)[0])
                .getBeanClassName());
    }
    
    private Field getPresenterField() {
        Field f;
        try {
            f = presenterClass.getDeclaredField(VIEW_FIELD_NAME);
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(presenterClass.getSuperclass());
        }
        return f;
    }

    private Field getInheritedPresenterField(Class<?> superclass) {
        Field f;
        try {
            f = superclass.getDeclaredField(VIEW_FIELD_NAME);
            int mod = f.getModifiers();
            if (!Modifier.isPublic(mod) && !Modifier.isProtected(mod)){
             // Wraps the exception, further detailing the reason behind it
                String message = String.format(
                        MvpInstantiationException.ERROR_NO_ACCESS_FORMAT,
                        presenterClass.getSimpleName(), VIEW_FIELD_NAME);
                throw new MvpInstantiationException(message);
            }
        } catch (NoSuchFieldException e) {
            f = getInheritedPresenterField(superclass.getSuperclass());
        } catch (NullPointerException e) {
         // Wraps the exception, further detailing the reason behind it
            ParameterizedType pt = (ParameterizedType)
                    presenterClass.getGenericSuperclass();
            Class<?> presenterInterface = (Class<?>) pt.getActualTypeArguments()[0];
            String presenterName = presenterInterface.getSimpleName();
            String message = String.format(
                    MvpInstantiationException.ERROR_NO_FIELD_FORMAT,
                    presenterClass.getSimpleName(), presenterName,
                    VIEW_FIELD_NAME);
            throw new MvpInstantiationException(message);
        }
        return f;
    }

}
