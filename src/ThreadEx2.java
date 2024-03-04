public class ThreadEx2 {
	public static void main(String[] args) {
		SharedResource1 sharedResource = new SharedResource1();
		
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new FirstThread("Thread " + (i + 1), sharedResource));
			thread.start();
		}
		
		// Wait for all threads to finish
		for (Thread thread : Thread.getAllStackTraces().keySet()) {
			if (thread.getName().startsWith("Thread ")) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		// Print the final counter value
		System.out.println("Final counter value: " + sharedResource.getCounter());
	}
}
class SharedResource1 {
	private int counter = 0;
	
	public synchronized void increment() {
		counter++;
	}
	
	public int getCounter() {
		return counter;
	}
}

class FirstThread implements Runnable{
	private final String name;
	private final SharedResource1 sharedResource;
	
	public FirstThread(String name, SharedResource1 sharedResource){
		this.name = name;
		this.sharedResource = sharedResource;
		Thread.currentThread().setName(name);
	}
	
	@Override
	public void run() {
		System.out.println("Thread " + Thread.currentThread().getName());
		
		// Increment the counter
		sharedResource.increment();
		
		Thread thread2 = new Thread(new SecondThread(sharedResource));
		thread2.start();
	}
}
class SecondThread implements Runnable {
	private final SharedResource1 sharedResource;
	
	public SecondThread(SharedResource1 sharedResource) {
		this.sharedResource = sharedResource;
	}
	
	@Override
	public void run() {
		System.out.println("Thread 2 is running");
		// Increment the counter
		sharedResource.increment();
	}
}
