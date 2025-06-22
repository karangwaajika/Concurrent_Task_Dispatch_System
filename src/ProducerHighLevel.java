import java.util.concurrent.BlockingQueue;

public class ProducerHighLevel implements Runnable{
    BlockingQueue<Task> queue = null;
    public ProducerHighLevel(BlockingQueue<Task> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            for (int item = 0; item < 10; item++) {
                Task currentTask = new Task("task-"+item, 2+item,
                        "task produced by "+threadName);
                queue.put(currentTask);
                System.out.printf("%s added %s !!\n", threadName, currentTask.getName());
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
