public class MainStackDriver {
    public static void main(String[] args) {
        SimpleBoundedStack<Integer> stack = new SimpleBoundedStack<>(5);

        // Producer thread pushing items onto the stack
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    stack.push(i);
                    System.out.println("Pushed: " + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Consumer thread popping items from the stack
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    int item = stack.pop();
                    System.out.println("Popped: " + item);
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}