import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerHighLevel implements Runnable{
    BlockingQueue<Task> queue = null;
    AtomicInteger taskNumber = new AtomicInteger(0); // to make task name unique
    public ProducerHighLevel(BlockingQueue<Task> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (int item = 0; item < 10; item++) {
                Task currentTask = new Task("task-"+taskNumber.incrementAndGet(), 2+item,
                        "task produced by "+threadName);
                queue.put(currentTask);
                System.out.printf("%s added %s !!\n", threadName, currentTask.getName());
                Thread.sleep(800);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted!");
        }

    }
}
