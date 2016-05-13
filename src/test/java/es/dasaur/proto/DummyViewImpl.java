package es.dasaur.proto;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DummyViewImpl extends ProtoView<DummyPresenter>
        implements DummyView {

    @Override
    public void init() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public DummyPresenter getPresenter() {
        return presenter;
    }

}
