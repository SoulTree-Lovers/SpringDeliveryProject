package org.delivery.storeadmin.domain.sse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.storeadmin.domain.authorization.model.UserSession;
import org.delivery.storeadmin.domain.sse.connection.SseConnectionPool;
import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseApiController {

    private final SseConnectionPool sseConnectionPool;
    private final ObjectMapper objectMapper;

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter connect(
        @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        log.info("logined user: {}", userSession);

        var userSseConnection = UserSseConnection.connect(
            userSession.getStoreId().toString(),
            sseConnectionPool,
            objectMapper
        );

        // 커넥션 풀에 추가
        sseConnectionPool.addSession(userSseConnection.getUniqueKey(), userSseConnection);

        return userSseConnection.getSseEmitter();


   /*     var emitter = new SseEmitter(1000L * 60); // 클라이언트와 SSE 연결이 되는 시점 (타임아웃 시간 설정 - ms단위임.)

        var temp = UserSseConnection.connect(userSession.getStoreId().toString(), sseConnectionPool);

        userConnection.put(userSession.getUserId().toString(), emitter); // 이미터 저장

        emitter.onTimeout(() -> {
            log.info("on timeout");
            // 클라이언트와 타임아웃이 일어났을 때
            emitter.complete(); // 연결 종료 호출
        });

        emitter.onCompletion(() -> {
            log.info("completion");
            // 클라이언트와 연결이 종료되었을 때
            userConnection.remove(userSession.getUserId().toString()); // 연결이 끊어지면 저장했던 이미터 삭제
        });

        // 최초 연결 시 응답 전송
        var event = SseEmitter
            .event()
            .name("onopen") // 클라이언트에게 onopen으로 응답을 보내야 함.
//            .data("connect")
            ;

        try {
            emitter.send(event); // 이벤트 전송
        } catch (IOException e) {
            emitter.completeWithError(e); // 에러 발생 시
        }

        return emitter; */
    }

    @GetMapping("/push-event")
    public void pushEvent(
        @Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession
    ) {
        // 기존 세션 가져오기
        var userSseConnection = sseConnectionPool.getSession(userSession.getStoreId().toString());

        Optional.ofNullable(userSseConnection)
            .ifPresent(it -> {
                it.sendMessage("hello world!");
            });
    }
}
