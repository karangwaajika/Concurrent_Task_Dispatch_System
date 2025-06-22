import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable{
    private BlockingQueue<Task> queue = null;
    int numberOfTask;
    private final AtomicInteger taskNumber = new AtomicInteger(0); // to make task name unique
    Random random = new Random();

    public Producer(BlockingQueue<Task> queue, int numberOfTask){
        this.queue = queue;
        this.numberOfTask = numberOfTask;
    }

    @Override
    public void run() {
        final int minPriority = 1;
        final int maxPriority = 5;

        String threadName = Thread.currentThread().getName();

        try {
            for (int item = 0; item < this.numberOfTask; item++) {
                int randomPriority = random.nextInt(maxPriority - minPriority + 1) + minPriority;
                Task currentTask = new Task("task-"+taskNumber.incrementAndGet(), randomPriority,
                        "task produced by "+threadName);
                queue.put(currentTask);
                System.out.printf("%s added %s !!\n", threadName, currentTask.getName());
                Thread.sleep(50);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted!");
        }

    }
}
