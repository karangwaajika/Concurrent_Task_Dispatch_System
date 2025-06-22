import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    BlockingQueue<Task> queue = null;
    int numberOfTask;

    public Consumer(BlockingQueue<Task> queue, int numberOfTask){
        this.queue = queue;
        this.numberOfTask = numberOfTask;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName()+"-Consumer";
        try {
            do {
                Task currentTask = queue.take();
                System.out.printf("%s Priority: %d consumed by %s \n", currentTask.getName(), currentTask.getPriority(), threadName);
            } while (queue.size() != this.numberOfTask); // stop consuming
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted!");
        }
    }
}
