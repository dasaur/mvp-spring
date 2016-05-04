package es.dasaur.mvp.spring.resources;

import es.dasaur.mvp.spring.Presenter;

public interface TestPresenter 
        extends Presenter<TestView, TestPresenter> {

    TestService getService();

    TestView getView();

}
