import java.util.concurrent.*;

public class FutureTask {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Future<Integer> future1 = executor.submit(new SumTask(1, 10));
        Future<Integer> future2 = executor.submit(new SumTask(11, 20));
        Future<Integer> future3 = executor.submit(new SumTask(21, 30));

        int sum = future1.get() + future2.get() + future3.get();

        System.out.println("The sum from 1 to 30 is: " + sum);

        executor.shutdown();
    }
}

class SumTask implements Callable<Integer> {
    private int start;
    private int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += i;
        }
        return sum;
    }
}