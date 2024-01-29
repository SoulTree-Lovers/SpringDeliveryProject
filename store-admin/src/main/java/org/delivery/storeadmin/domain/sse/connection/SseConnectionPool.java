package org.delivery.storeadmin.domain.sse.connection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolInterface;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SseConnectionPool implements ConnectionPoolInterface<String, UserSseConnection> {

    private static final Map<String, UserSseConnection> connectionPool = new ConcurrentHashMap<>();

    @Override
    public void addSession(String uniqueKey, UserSseConnection session) {
        connectionPool.put(uniqueKey, session);
    }

    @Override
    public UserSseConnection getSession(String uniqueKey) {
        return connectionPool.get(uniqueKey);
    }

    @Override
    public void onCompletionCallback(UserSseConnection session) {
        log.info("call back connection pool completion : {}", session); // 로그 출력해보기
        connectionPool.remove(session.getUniqueKey());
    }

}
