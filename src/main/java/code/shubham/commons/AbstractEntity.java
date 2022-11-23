package code.shubham.commons;

public abstract class AbstractEntity<Key> {
    protected Key id;

    public Key getId() {
        return id;
    }

    public void setId(Key id) {
        this.id = id;
    }
}
