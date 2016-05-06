package es.dasaur.proto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class A {
    
    private B b;
    
    @Autowired 
    private AutowireCapableBeanFactory beanFactory;
    
    public A(){
        b = new B(this);
        if(beanFactory != null) {
            beanFactory.autowireBean(b);
        }
    }

    A(B b){
        this.b = b;
    }
    
    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }

}
