package ru.fancytraffic.masterway.server.adm;

import org.eclipse.jetty.server.Server;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Roman Ljulchenko
 *         rnljul@gmail.com
 *         -----
 *         Created  on 02.03.2017.
 */
public class CommandExecutor {
    private final Server server;
    private ReentrantLock lock;
    private boolean isStopAvail;
    private Condition stopAvailCondition;
    private final String stopKey;

    public String getStopKey() {
        return stopKey;
    }

    public CommandExecutor(Server server, String stopKey) {
        this.stopKey = stopKey;
        this.lock = new ReentrantLock();
        this.isStopAvail = false;
        this.stopAvailCondition = lock.newCondition();
        this.server = server;
    }

    public void execStop() {
        lock.lock();
        isStopAvail = true;
        stopAvailCondition.signalAll();
        lock.unlock();

    }

    public void prepareStop() {
        lock.lock();
        try {
            while (!isStopAvail) {
                stopAvailCondition.await();
            }
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }


}
