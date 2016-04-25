package es.dasaur.mvp.spring.resources;

import javax.inject.Named;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import es.dasaur.mvp.spring.AbstractView;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TestViewImpl 
        extends AbstractView<TestPresenter>
        implements TestView {
    
    @Override
    public void init() {
        // no action
    }

    @Override
    public void refresh() {
        // no action
    }

    @Override
    public void close() {
        // no action
    }

    @Override
    public TestPresenter getPresenter() {
        return presenter;
    }

}
