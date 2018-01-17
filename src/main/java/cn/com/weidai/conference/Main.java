package cn.com.weidai.conference;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lwz on 2018/01/13 20:18.
 */
public class Main {

    public static final String      SHUTDOWN_HOOK_KEY = "server.shutdown.hook";
    private static volatile boolean running           = true;

    public static void main(String[] args) {
        long t = System.currentTimeMillis();

        try {
            final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:app.xml");
            ctx.start();
            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        try {
                            ctx.stop();
                        } catch (Throwable e) {
                        }

                        synchronized (Main.class) {
                            running = false;
                            Main.class.notify();
                        }
                    }
                });
            }

            System.out.println("spring已启动");

            ctx.getBean(WebSocketServer.class).start();
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (Main.class) {
            while (running) {
                try {
                    Main.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
