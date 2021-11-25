/*
 * Here should be licence
 */
package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author mcaikovs
 */
public class SupplyAsync {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("current thread: " + Thread.currentThread().getName());
        var future = CompletableFuture.supplyAsync(() -> Thread.currentThread().getName());
        System.out.println("current thread: " + Thread.currentThread().getName());
        System.out.println("task thread: " + future.get());
    }
}
