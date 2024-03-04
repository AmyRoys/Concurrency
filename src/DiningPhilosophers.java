import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    private static final int NUM_PHILOSOPHERS = 5;
    private static final Philosopher[] philosophers = new Philosopher[NUM_PHILOSOPHERS];
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophers[i] = new Philosopher(i);
            philosophers[i].start();
        }
    }

    static class Philosopher extends Thread {
        private final int id;
        private final Semaphore leftFork;
        private final Semaphore rightFork;

        Philosopher(int id) {
            this.id = id;
            this.leftFork = forks[id];
            this.rightFork = forks[(id + 1) % NUM_PHILOSOPHERS];
        }

        @Override
        public void run() {
            while (true) {
                try {
                    think();
                    eat();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking");
            Thread.sleep((long) (Math.random() * 10));
        }

        private void eat() throws InterruptedException {
            if (id == NUM_PHILOSOPHERS - 1) {
                // The last philosopher picks up the right fork first
                rightFork.acquire();
                System.out.println("Philosopher " + id + " picked up right fork");
                Thread.sleep((long) (Math.random() * 10));  // increase deadlock probability
                leftFork.acquire();
                System.out.println("Philosopher " + id + " picked up left fork");
            } else {
                // All other philosophers pick up the left fork first
                leftFork.acquire();
                System.out.println("Philosopher " + id + " picked up left fork");
                Thread.sleep((long) (Math.random() * 10));  // increase deadlock probability
                rightFork.acquire();
                System.out.println("Philosopher " + id + " picked up right fork");
            }
        
            System.out.println("Philosopher " + id + " is eating");
            Thread.sleep((long) (Math.random() * 10));
            leftFork.release();
            System.out.println("Philosopher " + id + " put down left fork");
            rightFork.release();
            System.out.println("Philosopher " + id + " put down right fork");
        }
    }
}