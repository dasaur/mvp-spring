package es.dasaur.mvp.spring.resources;

import javax.inject.Named;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import es.dasaur.mvp.spring.AbstractPresenter;

@Named
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TestPresenterImpl 
        extends AbstractPresenter<TestService, TestView>
        implements TestPresenter {

    public TestPresenterImpl() {
        super(TestViewImpl.class);
    }

    @Override
    public TestService getService() {
        return service;
    }

    @Override
    public TestView getView() {
        return view;
    }

}
