import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// We create a fixed thread pool with 3 threads
// We submit 5 tasks to the thread pool 

public class ThreadPoolExample {

    public static void main(String[] args) {
        System.out.println("Number of cores: " + Runtime.getRuntime().availableProcessors());
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit tasks to the thread pool
        for (int i = 0; i < 5; i++) {
            executor.submit(new Task(i));
        }

        // Shutdown the thread pool
        executor.shutdown();
    }

    static class Task implements Runnable {
        private final int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.println("Task " + taskId + " is being executed by Thread: " + Thread.currentThread().getName() + "\n");
            try {
                Thread.sleep(1000); // Simulate some task execution time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Task " + taskId + " has completed.");
        }
    }
}