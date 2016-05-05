package es.dasaur.mvp.spring.resources;

import es.dasaur.mvp.spring.AbstractView;
import es.dasaur.mvp.spring.annotations.View;

@View
public class TestViewImpl extends AbstractView<TestPresenter>
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
