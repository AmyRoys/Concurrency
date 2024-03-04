class SharedResource {
    private int count = 0;

    // Method is synchronized to ensure atomicity
    public synchronized void increment() {
        count++;
    }

    // Method is synchronized to ensure atomicity
    public synchronized int getCount() {
        return count;
    }
}

class Incrementer implements Runnable {
    private final SharedResource sharedResource;

    public Incrementer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            sharedResource.increment();
            System.out.println(Thread.currentThread().getName() + ": Incremented count to " + sharedResource.getCount());
            try {
                Thread.sleep(1000); // Simulating time taken to increment
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class SynchronizedExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        // Two threads incrementing the count in a synchronized manner
        Thread thread1 = new Thread(new Incrementer(sharedResource), "Thread-1");
        Thread thread2 = new Thread(new Incrementer(sharedResource), "Thread-2");

        thread1.start();
        thread2.start();
    }
}