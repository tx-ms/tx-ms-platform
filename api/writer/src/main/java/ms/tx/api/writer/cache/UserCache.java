package ms.tx.api.writer.cache;

public interface UserCache {

    void set(String username);

    boolean has(String username);
}
