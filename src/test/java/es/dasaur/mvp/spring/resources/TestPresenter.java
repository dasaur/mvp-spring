package es.dasaur.mvp.spring.resources;

import es.dasaur.mvp.spring.Presenter;

public interface TestPresenter extends Presenter<TestView> {

    TestService getService();

    TestView getView();

}
