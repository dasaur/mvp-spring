package es.dasaur.mvp.spring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import es.dasaur.mvp.spring.config.TestConfig;
import es.dasaur.mvp.spring.resources.TestPresenter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class MVPTest {
    
    @Inject
    TestPresenter presenter;
    
    @Inject
    TestPresenter otherPresenter;
    
    @Test
    public void referenceTest() {
        assertNotNull("Null presenter", presenter);
        assertNotNull("Null model", presenter.getService());
        assertNotNull("Null view", presenter.getView());
        assertNotNull("Null presenter reference at view", 
                presenter.getView().getPresenter());
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
