public class RaceCondition {
	private static int sharedCounter = 0;
	private static final int NUM_THREADS = 3;
	private static final int NUM_INCREMENTS = 1000;
	
	public static void main(String[] args) {
		// Create an array to hold threads
		Thread[] threads = new Thread[NUM_THREADS];
		
		// Create and start threads
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i] = new IncrementThread();
			threads[i].start();
		}
		
		// Wait for all threads to finish
		for (int i = 0; i < NUM_THREADS; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Display the final result
		System.out.println("Final Counter Value: " + sharedCounter);
	}
	
	// Thread class that increments the shared counter multiple times
	static class IncrementThread extends Thread {
		@Override
		public void run() {
			for (int i = 0; i < NUM_INCREMENTS; i++) {
				// Simulate some computation
				int temp = sharedCounter;
				// Introduce a delay to increase the chance of race condition
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sharedCounter = temp + 1;
			}
		}
	}
}