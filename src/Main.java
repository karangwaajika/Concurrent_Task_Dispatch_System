import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        final BlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        final int NUMBER_OF_TASK = 10;

        // producer
        Producer runnable = new Producer(queue, NUMBER_OF_TASK);
        Thread producerHighLevel1 = new Thread(runnable);
        Thread producerHighLevel2 = new Thread(runnable);

        try{
            producerHighLevel1.start();
            producerHighLevel1.join();

            producerHighLevel2.start();
            producerHighLevel2.join();
            System.out.println(queue.size());
            System.out.println(queue.stream().map(i->i.getName()+" "+i.getPriority()).toList());
        }catch (InterruptedException e){
            System.out.println( e.getMessage());
        }

        // consumer
        AtomicInteger consumedCount = new AtomicInteger(0);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Consumer(queue, consumedCount, queue.size()));
        executor.submit(new Consumer(queue, consumedCount, queue.size()));

        executor.shutdown();

    }
}