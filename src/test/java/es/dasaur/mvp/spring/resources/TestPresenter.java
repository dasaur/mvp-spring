package es.dasaur.mvp.spring.resources;

import es.dasaur.mvp.spring.Presenter;

public interface TestPresenter 
        extends Presenter<TestService, TestView, TestPresenter> {

    TestService getService();

    TestView getView();

}
