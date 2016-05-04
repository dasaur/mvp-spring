package es.dasaur.mvp.spring.resources;

import es.dasaur.mvp.spring.View;

public interface TestView
        extends View <TestView, TestPresenter> {

    TestPresenter getPresenter();
    
    boolean getInitiated();
    
}
