package es.dasaur.proto;

import es.dasaur.mvp.spring.View;

public interface DummyView extends View<DummyPresenter> {
    
    DummyPresenter getPresenter();

}
