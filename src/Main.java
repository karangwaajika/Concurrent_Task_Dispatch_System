import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Task> queue = new PriorityBlockingQueue<>();

        ProducerHighLevel runnable = new ProducerHighLevel(queue);
        Thread producerHighLevel1 = new Thread(runnable);
        Thread producerHighLevel2 = new Thread(runnable);
        try{
            producerHighLevel1.start();
            producerHighLevel1.join();

            producerHighLevel2.start();
            producerHighLevel2.join();
            System.out.println(queue.size());
        }catch (InterruptedException e){
            System.out.println( e.getMessage());
        }

    }
}