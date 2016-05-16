package es.dasaur.mvp.spring;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.dasaur.mvp.spring.config.TestConfig;
import es.dasaur.mvp.spring.resources.TestPresenter;
import es.dasaur.mvp.spring.resources.TestView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MVPTest {
    
    @Autowired
    TestPresenter presenter;
    
    @Autowired
    TestPresenter otherPresenter;
    
    @Autowired
    TestView view;
    
    @Test
    public void presenterReferenceTest() {
        assertNotNull("Null presenter", presenter);
        assertNotNull("Null model", presenter.getService());
        assertNotNull("Null view", presenter.getView());
        assertTrue("View did not initiate on construction", 
                presenter.getView().getInitiated());
        assertNotNull("Null presenter reference at view", 
                presenter.getView().getPresenter());
        assertEquals("View's presenter is not the presenter itself", 
                presenter, presenter.getView().getPresenter());
    }
    
    @Test
    public void viewReferenceTest() {
        assertNotNull("Null view", view);
        assertNotNull("Null presenter", view.getPresenter());
        assertNotNull("Null model", view.getPresenter().getService());
        assertTrue("View did not initiate on construction", 
                view.getInitiated());
        assertNotNull("Null view reference at presenter", 
                view.getPresenter().getView());
        assertEquals("Presenter's view is not the view itself", 
                presenter, presenter.getView().getPresenter());
    }
    
    @Test
    public void scopeTest() {
        assertNotNull("Null presenter", presenter);
        assertTrue("Non-singleton model", 
                presenter.getService() == otherPresenter.getService());
        assertFalse("Non-prototype presenter", 
                presenter.equals(otherPresenter));
        assertFalse("Non-prototype presenter", 
                presenter.getView().equals(otherPresenter.getView()));
    }

}
