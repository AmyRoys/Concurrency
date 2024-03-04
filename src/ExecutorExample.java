import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorExample {

    public static void main(String[] args) {
        // Creating an Executor using the newCachedThreadPool() method
        Executor executor = Executors.newCachedThreadPool();

        // Submitting tasks to the Executor
        for (int i = 0; i < 5; i++) {
            executor.execute(new Task(i));
        }
    }

    // Custom task class implementing the Runnable interface
    static class Task implements Runnable {
        private final int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.println("Task " + taskId + " is executing by " + Thread.currentThread().getName());
        }
    }
}