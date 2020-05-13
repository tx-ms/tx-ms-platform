package ms.tx.api.writer.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
/*
 * Since it is caching data, which is not so intensive,
 * we do not care about cache misses. It is a known issue,
 * that each replica of the service will have it's own cache.
 * For next version, we can implement redis/memcached.
 */
public class SimpleNonDistributedCache implements UserCache {

    private final Map<String, Boolean> users;

    public SimpleNonDistributedCache() {
        this.users = new ConcurrentHashMap<>();
    }

    @Override
    public void set(String username) {
        this.users.put(username, true);
    }

    @Override
    public boolean has(String username) {
        return this.users.containsKey(username);
    }
}
