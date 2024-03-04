public class ThreadEx {
	public static void main(String[] args) {
			Thread thread = new Thread(new ThreadOne("Thread 1"));
			thread.start();
	}
}
class ThreadOne implements Runnable{
	private final String name;
	
	public ThreadOne(String name){
		this.name = name;
		
		Thread.currentThread().setName(name);
	}
	@Override
	public void run() {
		//System.out.println("Thread 1 is running" + Thread.currentThread().getName());
		
		Thread thread2 = new Thread(new ThreadTwo());
		thread2.start();
		
	}
	
}
class ThreadTwo implements Runnable {
	@Override
	public void run() {
		//System.out.println("Thread 2 is running");
		
		//throw new RuntimeException("Intentional Exception in Thread 2");
	}
	
}