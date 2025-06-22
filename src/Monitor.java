import java.time.Instant;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class Monitor implements Runnable{

    private BlockingQueue<Task> queue = null;
    private ThreadPoolExecutor pool = null;
    public Monitor(BlockingQueue<Task> queue, ThreadPoolExecutor pool){
        this.queue = queue;
        this.pool = pool;
    }
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);

                int  qSize      = queue.size();
                int  poolSize   = pool.getPoolSize();     // workers
                int  active     = pool.getActiveCount();  // currently executing
                long completed  = pool.getCompletedTaskCount();
                int  largest    = pool.getLargestPoolSize();

                System.out.printf(
                        "Monitor => queue=%d | pool=%d | active=%d | largest=%d | completed=%d%n",
                        qSize, poolSize, active, largest, completed);

            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
