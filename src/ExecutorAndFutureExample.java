import java.util.concurrent.*;

public class ExecutorAndFutureExample {

    public static void main(String[] args) {
        // Create an executor with a fixed thread pool of size 1
        ExecutorService executor = Executors.newFixedThreadPool(1);

        // Submit a task that returns a result
        Future<Integer> futureResult = executor.submit(() -> {
            // Simulate a long-running computation
            Thread.sleep(2000);
            return 42;
        });

        // Retrieve the result
        try {
            for (int i = 1; i <= 5; i++) {
               System.out.println("do work " + i);
               Thread.sleep(100);
            }
            System.out.println(" Lets check to see is the task complete and we have an answer");
            Integer result = futureResult.get();
            System.out.println("Result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Shutdown the executor
        executor.shutdown();
    }
}