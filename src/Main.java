import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        Producer runnable = new Producer(queue);
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

    }
}