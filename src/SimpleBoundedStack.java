import java.util.Stack;

public class SimpleBoundedStack<T> {
    private Stack<T> stack;
    private int maxSize;

    public SimpleBoundedStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new Stack<>();
    }

    public synchronized void push(T item) throws InterruptedException {
        if (stack.size() == maxSize) {
            System.out.println("Stack is empty, waiting...");
            wait();
        }
        stack.push(item);
        notifyAll();
    }

    public synchronized T pop() throws InterruptedException {
        if (stack.isEmpty()) {
            System.out.println("Calling wait pop");
            wait();
        }
        T item = stack.pop();
        notifyAll();
        return item;
    }

    public synchronized int size() {
        return stack.size();
    }
}