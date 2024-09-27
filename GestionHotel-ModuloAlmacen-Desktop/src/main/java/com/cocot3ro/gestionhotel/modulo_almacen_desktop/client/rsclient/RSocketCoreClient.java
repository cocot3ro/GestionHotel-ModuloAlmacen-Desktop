package com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.rsclient;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.ICoreClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RSocketCoreClient implements ICoreClient {
    private final RSocketRequester rSocketRequester;

    @Setter
    private Runnable onConnectError;

    @Override
    public Mono<String> checkConnection() {
        return rSocketRequester.route("/status")
                .retrieveMono(String.class)
                .doOnError(e -> {
                    if (onConnectError != null) {
                        onConnectError.run();
                    }
                });
    }
}
