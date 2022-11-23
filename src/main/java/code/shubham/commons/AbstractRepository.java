package code.shubham.commons;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractRepository<Object extends AbstractEntity<Key>, Key> {
    protected final AtomicInteger idGenerator = new AtomicInteger(1);

    private final Map<Key, Object> primaryIndex = new ConcurrentHashMap<>();

    protected final LockService<Key> keyLockService = new LockService<>();

    public Object add(Object object) {
        if (object.getId() == null)
            this.setId(object);
        try {
            this.keyLockService.getLock(object.getId()).writeLock().lock();
            this.primaryIndex.put(object.getId(), object);
            this.addToIndexes(object);
        } finally {
            this.keyLockService.getLock(object.getId()).writeLock().unlock();
        }
        return object;
    }

    protected abstract void setId(Object object);

    protected abstract void addToIndexes(Object object);

    public Object getById(Key id) {
        return this.primaryIndex.get(id);
    }

    public boolean remove(Key id) {
        Object object = this.primaryIndex.get(id);
        if (object == null)
            return true;
        this.removeFromIndexes(object);
        this.primaryIndex.remove(id);
        return true;
    }

    protected abstract void removeFromIndexes(Object object);

    protected void lockWrite(Object object) {
        this.keyLockService.getLock(object.getId()).writeLock().lock();
    }

    protected void unlockWrite(Object object) {
        this.keyLockService.getLock(object.getId()).writeLock().unlock();
    }

    protected void lockRead(Object object) {
        this.keyLockService.getLock(object.getId()).readLock().lock();
    }

    protected void unlockRead(Object object) {
        this.keyLockService.getLock(object.getId()).readLock().unlock();
    }

    protected void lockWrite(Key id) {
        this.keyLockService.getLock(id).writeLock().lock();
    }

    protected void unlockWrite(Key id) {
        this.keyLockService.getLock(id).writeLock().unlock();
    }

    protected void lockRead(Key id) {
        this.keyLockService.getLock(id).readLock().lock();
    }

    protected void unlockRead(Key id) {
        this.keyLockService.getLock(id).readLock().unlock();
    }
}
