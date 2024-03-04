class SharedResource {
    private int data;
    private boolean dataProduced = false;

    public synchronized void produce(int value) {
        while (dataProduced) {
            try {
                wait(); // Wait if data is already produced
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        data = value;
        System.out.println("Produced: " + data);
        dataProduced = true;

        notify(); // Notify waiting consumer
    }

    public synchronized int consume() {
        while (!dataProduced) {
            System.out.println("Calling consume wait");
            try {
                wait(); // Wait if data is not yet produced
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Consumed: " + data);
        dataProduced = false;  

        notify(); // Notify waiting producer

        return data;
    }
}

class Producer implements Runnable {
    private final SharedResource sharedResource;

    public Producer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.produce(i);
            try {
                Thread.sleep(1000); // Simulating time taken to produce
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private final SharedResource sharedResource;

    public Consumer(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            sharedResource.consume();
            // try {
            //     Thread.sleep(1500); // Simulating time taken to consume
            // } catch (InterruptedException e) {
            //     Thread.currentThread().interrupt();
            // }
        }
    }
}

class ProducerConsumerWaitNotifyExample {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        Thread producerThread = new Thread(new Producer(sharedResource));
        
        Thread consumerThread = new Thread(new Consumer(sharedResource));

        Thread producerThread1 = new Thread(new Producer(sharedResource));
        
        Thread consumerThread1 = new Thread(new Consumer(sharedResource));

        producerThread.start();
        producerThread1.start();
        consumerThread.start();
        consumerThread1.start();
    }
}