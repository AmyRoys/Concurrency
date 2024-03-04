import java.util.concurrent.ArrayBlockingQueue;

public class SimpleBoundedQueue<T> {
    private ArrayBlockingQueue<T> queue;

    public SimpleBoundedQueue(int maxSize) {
        this.queue = new ArrayBlockingQueue<>(maxSize);
    }

    public void push(T item) throws InterruptedException {
        if (queue.remainingCapacity() == 0) {
            pop();
        }
        queue.put(item);
    }
    public T pop() throws InterruptedException {
        return queue.take();
    }

    public static void main(String[] args) {
        SimpleBoundedQueue<Integer> queue = new SimpleBoundedQueue<>(5);

        // Producer Thread
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    queue.push(i);
                    System.out.println("Pushed: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Consumer Thread
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Integer value = queue.pop();
                    System.out.println("Popped: " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}