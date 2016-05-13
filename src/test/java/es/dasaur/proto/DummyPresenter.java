package es.dasaur.proto;

import es.dasaur.mvp.spring.Presenter;

public interface DummyPresenter extends Presenter<DummyView>{
    
    DummyView getView();

}
