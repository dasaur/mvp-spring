package es.dasaur.mvp.spring.resources;

import org.springframework.beans.factory.annotation.Autowired;

import es.dasaur.mvp.spring.AbstractPresenter;
import es.dasaur.mvp.spring.annotations.Presenter;

@Presenter
public class TestPresenterImpl extends AbstractPresenter<TestView>
        implements TestPresenter {

    @Autowired
    private TestService service;

    @Override
    public TestService getService() {
        return service;
    }

    @Override
    public TestView getView() {
        return view;
    }

}
