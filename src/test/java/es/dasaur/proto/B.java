package es.dasaur.proto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class B {

    @Autowired 
    private AutowireCapableBeanFactory beanFactory;
    
    private A a;
    
    public B() {
        a = new A(this);
        if(beanFactory != null) {
            beanFactory.autowireBean(a);
        }
    }
    
    B(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
