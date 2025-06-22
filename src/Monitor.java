import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Monitor implements Runnable{

    private BlockingQueue<Task> queue = null;
    private Thread currentThread = null;
    public Monitor(BlockingQueue<Task> queue, Thread currentThread){
        this.queue = queue;
        this.currentThread = currentThread;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                int queueSize = queue.size();
                String threadStatus = currentThread.getState().toString();
                System.out.printf("Monitor: queueSize = %d, Thread name: %s, thread status= %s \n",
                        queueSize, currentThread.getName(), threadStatus);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
