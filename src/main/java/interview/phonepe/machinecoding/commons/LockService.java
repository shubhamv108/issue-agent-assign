package interview.phonepe.machinecoding.commons;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockService<Key> {
    private Map<Key, ReentrantReadWriteLock> keys = new HashMap<>();

    public ReentrantReadWriteLock getLock(Key key) {
        var lock = keys.get(key);
        if (lock != null)
            return lock;
        synchronized (key) {
            this.keys.put(key, lock = new ReentrantReadWriteLock());
        }
        return lock;
    }
}
