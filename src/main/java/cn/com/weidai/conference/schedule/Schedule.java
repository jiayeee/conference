package cn.com.weidai.conference.schedule;

import cn.com.weidai.conference.media.Cmds;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.concurrent.*;

/**
 * Created by lwz on 2018/01/16 17:09.
 */
@Repository
public class Schedule implements InitializingBean {

    private ExecutorService     threadPool = Executors.newFixedThreadPool(10);

    private BlockingQueue<Task> queue      = new LinkedBlockingQueue<>();

    private volatile boolean    shutDown   = false;

    public void addTask(final Task task) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    queue.put(task);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void shutDown() {
        this.shutDown = true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                while (!shutDown) {
                    try {
                        Task task = queue.take();
                        int cmd = task.getCmd();
                        switch (cmd) {
                            case Cmds.CMD_HTTP_LUCKY_DRAW:
                                threadPool.submit(new LuckDrawBiz(task));
                                break;
                            default:
                                break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
