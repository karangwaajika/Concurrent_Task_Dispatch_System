import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerLowLevel implements Runnable{
    BlockingQueue<Task> queue = null;
    AtomicInteger taskNumber = new AtomicInteger(0); // to make task name unique

    public ProducerLowLevel(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

    }
}
