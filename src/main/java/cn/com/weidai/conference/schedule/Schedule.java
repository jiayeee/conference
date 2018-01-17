package cn.com.weidai.conference.schedule;

import cn.com.weidai.conference.media.CMDs;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.*;

/**
 * Created by lwz on 2018/01/16 17:09.
 */
@Repository
public class Schedule implements InitializingBean {

    private ExecutorService     executor = Executors.newFixedThreadPool(5);

    private BlockingQueue<Task> queue    = new LinkedBlockingQueue<>();

    private volatile boolean    shutDown = false;

    @Autowired
    private HttpBiz             httpBiz;

    public void addTask(final Task task) {
        executor.execute(new Runnable() {
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!shutDown) {
                    try {
                        Task task = queue.take();
                        int cmd = task.getCmd();
                        switch (cmd) {
                            case CMDs.CMD_HTTP_LUCKY_DRAW:
                                httpBiz.luckDraw(task);
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
