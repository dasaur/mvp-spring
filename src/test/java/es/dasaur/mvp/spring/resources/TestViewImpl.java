package es.dasaur.mvp.spring.resources;

import javax.inject.Named;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import es.dasaur.mvp.spring.AbstractView;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TestViewImpl 
        extends AbstractView<TestView, TestPresenter>
        implements TestView {
    
    boolean initiated = false;
    
    @Override
    public void init() {
        initiated = true;
    }

    @Override
    public TestPresenter getPresenter() {
        return presenter;
    }

    @Override
    public boolean getInitiated() {
        return initiated;
    }

}
