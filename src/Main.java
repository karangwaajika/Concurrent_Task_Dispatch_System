import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        final BlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        final int NUMBER_OF_TASK = 10;
        ConcurrentHashMap<UUID, Task.TaskStatus> statusMap = new ConcurrentHashMap<>();

        // producer
        Producer runnable = new Producer(queue, NUMBER_OF_TASK, statusMap);
        Thread producerHighLevel1 = new Thread(runnable);
        Thread producerHighLevel2 = new Thread(runnable);


        try{
            producerHighLevel1.start();
            producerHighLevel1.join();

            producerHighLevel2.start();
            producerHighLevel2.join();
            System.out.println("Total Size produced:"+ queue.size());
//            System.out.println(queue.stream().map(i->i.getName()+" "+i.getPriority()).toList());
        }catch (InterruptedException e){
            System.out.println( e.getMessage());
        }

        System.out.println("######################## CONSUMER ################# ");

        // consumer
        AtomicInteger consumedCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Consumer(queue, consumedCount, queue.size(), statusMap));
        executor.submit(new Consumer(queue, consumedCount, queue.size(), statusMap));

        executor.shutdown();

        // ---- safe down-cast ----
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;

        //monitor
        Monitor runnableMonitor = new Monitor(queue, pool);
        Thread monitor = new Thread(runnableMonitor);
        monitor.start();

    }
}