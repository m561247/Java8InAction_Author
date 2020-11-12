package lambdasinaction.chap11;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SelfPractice {
	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		// Before java8
		ExecutorService exec = Executors.newCachedThreadPool();
		Future<Double> future = exec.submit(new Callable<Double>() {
			public Double call() {
				return 1.0;
			}
		});
		// do something else
		Double result = future.get(10, TimeUnit.SECONDS);
		System.out.println("result = " + result);
		
	}
}
