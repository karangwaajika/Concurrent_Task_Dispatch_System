package lowLevel;

import java.util.List;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{
    List<Task> buffer = null; //not synchronized
    int bufferCapacity = 0;
    AtomicInteger taskNumber = new AtomicInteger(0); // to make task name unique
    Random random = new Random();
    public Producer(List<Task> buffer, int bufferCapacity) {

        this.buffer = buffer;
        this.bufferCapacity = bufferCapacity;
    }

    @Override
    public void run() {
        final int minPriority = 1;
        final int maxPriority = 5;

        String threadName = Thread.currentThread().getName()+" low-level";

        try {
            for (int item = 0; item < 10; item++) {
                synchronized (buffer){

                    while (buffer.size() == this.bufferCapacity) {
                        buffer.wait(); // block when full
                    }

                    int randomPriority = random.nextInt(maxPriority - minPriority + 1) + minPriority;
                    Task currentTask = new Task("task-" + taskNumber.incrementAndGet(), randomPriority,
                            "task produced by "+threadName);

                    buffer.add(currentTask);

                    System.out.printf("%s added %s !!\n", threadName, currentTask.getName());

                    buffer.notifyAll(); // wake consumer

                }
                Thread.sleep(500); // timeout before adding a new one
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted!");
        }

    }
}
