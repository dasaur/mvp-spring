package es.dasaur.proto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import es.dasaur.mvp.spring.Presenter;

@Component
@Scope("prototype")
public class DummyPresenterImpl extends ProtoPresenter<DummyView> 
        implements DummyPresenter {

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setTitle(String title) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Presenter<?> getParentPresenter() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setParentPresenter(Presenter<?> mainPresenter) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public DummyView getView() {
        return view;
    }

}
