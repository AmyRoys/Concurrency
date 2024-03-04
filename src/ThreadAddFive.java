// Define a class that extends the Thread class
class MyThread extends Thread {
	// Override the run method with the code to be executed by the thread
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread is running: " + i);
			try {
				// Adding a short delay for better visibility
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class ThreadAddFive {
	public static void main(String[] args) {
		// Create an instance of the class that extends Thread
		MyThread myThread = new MyThread();
		
		// Start the thread
		myThread.start();
		
		// Code in the main thread
		for (int i = 1; i <= 3; i++) {
			System.out.println("Main thread is running: " + i);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}