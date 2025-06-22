package lowLevel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable{
    List<Task> buffer = null; //not synchronized
    int numberOfTask;
    int bufferCapacity;

    public Consumer(List<Task> buffer, AtomicInteger consumedCount, int numberOfTask) {
        this.buffer = buffer;
        this.numberOfTask = numberOfTask;
        this.consumedCount = consumedCount;
    }

    private final AtomicInteger consumedCount;
    @Override
    public void run() {
        String threadName = Thread.currentThread().getName()+"-Consumer";
        int threadConsumedCount = 0;
        try {
            while (true) {
                Task task = null;
                synchronized (buffer) {
                    while (buffer.isEmpty()) {
                        buffer.wait(); //wait when empty
                    }
                    task = buffer.remove(0);
                    buffer.notifyAll();                        // wake producers
                }
                int totalConsumed = consumedCount.incrementAndGet();
                threadConsumedCount++;

                System.out.printf("[%s] Consumed %s (my count: %d, total: %d/%d, priority: %d,time: %s)\n",
                        threadName, task.getName(), threadConsumedCount, totalConsumed,
                        numberOfTask, task.getPriority() ,task.getTime());
                System.out.printf("%s consumed %s%n",threadName, task.getName());
                Thread.sleep(500);                             // simulate processing
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted!");
        }
    }

}
