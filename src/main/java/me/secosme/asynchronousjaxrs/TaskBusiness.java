package me.secosme.asynchronousjaxrs;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

/**
 * JAVA 8: http://fahdshariff.blogspot.com.co/2016/06/java-8-completablefuture-vs-parallel.html
 * IBM: https://www.ibm.com/support/knowledgecenter/en/SSAW57_liberty/com.ibm.websphere.wlp.nd.multiplatform.doc/ae/twlp_config_managedexecutor.html
 * 
 * @author developer
 */
@Stateless
public class TaskBusiness {

    @Inject
    private ExpensiveTaskBusiness business;

    @Resource
    private ManagedExecutorService pool;

    @Asynchronous
    public void execute() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        long start = System.nanoTime();

        List<CompletableFuture<Integer>> completables = IntStream.range(0, 10)
            .mapToObj(t -> CompletableFuture.supplyAsync(() -> business.calculate(3), pool))
            .collect(Collectors.toList());

        List<Integer> result = completables.stream()
            .map(CompletableFuture::join)
            .collect(Collectors.toList());

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("%s - Processed %d tasks in %d millis\n", Thread.currentThread(), result.size(), duration);

    }
}
