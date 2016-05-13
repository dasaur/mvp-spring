package es.dasaur.proto;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ProtoConfig.class)
public class ProtoTest {
    
    @Autowired
    private A aBean;
    
    @Autowired
    private B bBean;
    
    @Autowired
    private DummyView dummyView;
    
    @Test
    public void test() {
        assertNotNull("null aBean", aBean);
        assertNotNull("null bBean", bBean);
        assertNotNull("null b in aBean", aBean.getB());
        assertNotNull("null a in bBean", bBean.getA());
        assertNotEquals("aBean = bBean.a", aBean, bBean.getA());
        assertNotEquals("bBean = aBean.b", bBean, aBean.getB());
        assertEquals("aBean != aBean.b.a", aBean, aBean.getB().getA());
        assertEquals("bBean != bBean.a.b", bBean, bBean.getA().getB());
    }
}
