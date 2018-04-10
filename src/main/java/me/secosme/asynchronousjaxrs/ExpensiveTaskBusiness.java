package me.secosme.asynchronousjaxrs;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExpensiveTaskBusiness {

    public Integer calculate(Integer duration) {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(duration * 1000);
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
        return duration;
    }

}
