public class IsAliveExample {
    public static void main(String[] args) {
        Thread thread = new Thread(new MyRunnable());
        
        System.out.println("Thread is alive before starting: " + thread.isAlive());
        
        thread.start();
        
        System.out.println("Thread is alive after starting: " + thread.isAlive());
        
        try {
            thread.join(); // Wait for the thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Thread is alive after joining: " + thread.isAlive());
        // Attempting to restart the thread
        //thread.start();

    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running");
        try {
            Thread.sleep(2000); // Simulate some work being done by the thread
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread is finished");
    }
}