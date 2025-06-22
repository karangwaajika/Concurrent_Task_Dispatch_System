import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        final int NUMBER_OF_TASK = 10;
        ConcurrentHashMap<UUID, Task.TaskStatus> statusMap = new ConcurrentHashMap<>();

        // producer
        Producer runnable = new Producer(queue, NUMBER_OF_TASK, statusMap);
        Thread producer1 = new Thread(runnable);
        Thread producer2 = new Thread(runnable);

        producer1.start();
        producer2.start();

        // Wait for producers to finish
        producer1.join();
        producer2.join();

        System.out.println("######################## CONSUMER ################# ");

        // consumer
        AtomicInteger consumedCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Consumer(queue, consumedCount, queue.size(), statusMap));
        executor.submit(new Consumer(queue, consumedCount, queue.size(), statusMap));


        // Monitor
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor; // cast executor
        Thread monitor = new Thread(new Monitor(queue, pool), "monitor");
        monitor.start();


        // shutdown consumer
//        pool.shutdown();
//        // stop monitor
//        monitor.interrupt();

    }
}