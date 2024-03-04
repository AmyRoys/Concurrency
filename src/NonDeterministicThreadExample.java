import java.util.Random;

public class NonDeterministicThreadExample {
	
	public static void main(String[] args) {
		SharedResource sharedResource = new SharedResource();
		
		Thread thread1 = new Thread(new Worker(sharedResource, "Thread 1"));
		Thread thread2 = new Thread(new Worker(sharedResource, "Thread 2"));
		//shows the main thread
		System.out.println(Thread.currentThread().getName());
		
		thread1.start();
		thread2.start();
		
		try{
			thread1.join();
			thread2.join();
		}catch (InterruptedException e){
			e.printStackTrace();
		}
	}
}


class SharedResource {
	private int counter = 0;
	
	public void increment() {
		int temp = counter;
		// Simulate some non-atomic operation
		try {
			Thread.sleep(new Random().nextInt(50));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter = temp + 1;
		System.out.println(Thread.currentThread().getName() + " - Counter: " + counter);
	}
}

class Worker implements Runnable {
	private final SharedResource sharedResource;
	private final String threadName;
	
	public Worker(SharedResource sharedResource, String threadName) {
		this.sharedResource = sharedResource;
		this.threadName = threadName;
	}
	@Override
	public void run() {
		System.out.println(threadName + " priority: " + Thread.currentThread().getPriority());
		for (int i = 0; i < 5; i++) {
			sharedResource.increment();
		}
	}
	//finds the name of the garbage collector thread
	@Override
	protected void finalize(){
		String name = (Thread.currentThread().getName());
		System.out.println("Finalize: + name");
	}
}