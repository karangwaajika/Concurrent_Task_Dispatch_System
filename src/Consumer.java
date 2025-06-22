import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable{
    BlockingQueue<Task> queue = null;
    int numberOfTask;
    private final AtomicInteger consumedCount;
    ConcurrentHashMap<UUID, Task.TaskStatus> statusMap;

    public Consumer(BlockingQueue<Task> queue, AtomicInteger consumedCount, int numberOfTask,
                    ConcurrentHashMap<UUID, Task.TaskStatus> statusMap){
        this.queue = queue;
        this.numberOfTask = numberOfTask;
        this.consumedCount = consumedCount;
        this.statusMap = statusMap;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName()+"-Consumer";
        int threadConsumedCount = 0;
        try {
            while (consumedCount.get() < numberOfTask) {
                Task task = queue.take();
                statusMap.put(task.getId(), Task.TaskStatus.PROCESSING);
                System.out.println(task.getName()+ " Task being processed");

                int totalConsumed = consumedCount.incrementAndGet();
                threadConsumedCount++;

                System.out.printf("[%s] Consumed %s (my count: %d, total: %d/%d, priority: %d,time: %s)\n",
                        threadName, task.getName(), threadConsumedCount, totalConsumed,
                        numberOfTask, task.getPriority() ,task.getTime());

                statusMap.put(task.getId(), Task.TaskStatus.COMPLETED);
                System.out.println(task.getName()+ " Task completed");

                // Simulate processing time
                Thread.sleep(500);

//                if (totalConsumed >= numberOfTask) {
//                    System.out.printf("[%s] Target reached! Stopping. (my count: %d)\n",
//                            threadName, threadConsumedCount);
//                    break;
//                }
            }
            System.out.printf("[%s] ✅ COMPLETED! Successfully consumed %d tasks\n",
                    threadName, threadConsumedCount);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted!");
        }
    }
}
