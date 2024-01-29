package org.delivery.storeadmin.domain.sse.connection.model;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.ifs.ConnectionPoolInterface;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Getter
@ToString
@EqualsAndHashCode()
public class UserSseConnection {

    private final String uniqueKey;

    private final SseEmitter sseEmitter;

    private final ObjectMapper objectMapper;

    private final ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface;


    private UserSseConnection(String uniqueKey, ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface, ObjectMapper objectMapper) {
        this.uniqueKey = uniqueKey; // key 초기화

        this.sseEmitter = new SseEmitter(60 * 1000L); // sse 초기화

        this.connectionPoolInterface = connectionPoolInterface; // call back 초기화

        this.objectMapper = objectMapper; // object mapper 초기화

        // on completion
        this.sseEmitter.onCompletion(() -> {
            // connection pool에서 삭제
            connectionPoolInterface.onCompletionCallback(this);
        });

        // on timeout
        this.sseEmitter.onTimeout(() -> {
            this.sseEmitter.complete();
        });

        // onopen 메시지
        sendMessage("onopen", "connect"); // data는 의미 없음.


    }

    public static UserSseConnection connect(String uniqueKey, ConnectionPoolInterface<String, UserSseConnection> connectionPoolInterface, ObjectMapper objectMapper) {
        return new UserSseConnection(uniqueKey, connectionPoolInterface, objectMapper);
    }

    public void sendMessage(String eventName, Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data); // json 형식으로 변경

            var event = SseEmitter.event()
                .name(eventName)
                .data(json)
                ;

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }

    public void sendMessage(Object data) {
        try {
            var json = this.objectMapper.writeValueAsString(data); // json 형식으로 변경

            var event = SseEmitter.event()
                .data(json)
                ;

            this.sseEmitter.send(event);
        } catch (IOException e) {
            this.sseEmitter.completeWithError(e);
        }
    }
}
