package code.shubham.commons;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockService<Key> {
    private final Map<Key, ReentrantReadWriteLock> keys = new ConcurrentHashMap<>();

    public ReentrantReadWriteLock getLock(Key key) {
        var lock = keys.get(key);
        if (lock != null)
            return lock;
        synchronized (key) {
            this.keys.put(key, lock = new ReentrantReadWriteLock());
        }
        return lock;
    }

    protected void lockWrite(Key key) {
        this.getLock(key).writeLock().lock();
    }

    protected void unlockWrite(Key key) {
        this.getLock(key).writeLock().unlock();
    }

    protected void lockRead(Key key) {
        this.getLock(key).readLock().lock();
    }

    protected void unlockRead(Key key) {
        this.getLock(key).readLock().unlock();
    }
}
