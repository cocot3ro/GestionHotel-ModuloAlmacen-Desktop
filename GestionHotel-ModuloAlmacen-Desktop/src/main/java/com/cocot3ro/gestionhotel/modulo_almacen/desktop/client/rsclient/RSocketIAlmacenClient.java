package com.cocot3ro.gestionhotel.modulo_almacen.desktop.client.rsclient;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.client.IAlmacenClient;
import com.cocot3ro.gestionhotel.modulo_almacen.desktop.model.AlmacenModel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RSocketIAlmacenClient implements IAlmacenClient {

    private final RSocketRequester rSocketRequester;

    @Setter
    private Runnable onConnectError;

    @Override
    public Flux<AlmacenModel[]> getAlmacenEntries() {
        return rSocketRequester.route("/all")
                .retrieveFlux(AlmacenModel[].class);
    }

    @Override
    public Mono<AlmacenModel> saveAlmacenEntry(AlmacenModel item) {
        return rSocketRequester.route("/save")
                .data(item)
                .retrieveMono(AlmacenModel.class)
                .doOnError(e -> {
                    if (onConnectError != null) {
                        onConnectError.run();
                    }
                });
    }

    @Override
    public Mono<AlmacenModel> updateAlmacenEntry(AlmacenModel item) {
        return rSocketRequester.route("/update")
                .data(item)
                .retrieveMono(AlmacenModel.class)
                .doOnError(e -> {
                    if (onConnectError != null) {
                        onConnectError.run();
                    }
                });
    }

    @Override
    public void deleteAlmacenEntry(AlmacenModel item) {
        rSocketRequester.route("/delete")
                .data(item.getId())
                .retrieveMono(AlmacenModel.class)
                .doOnError(e -> {
                    if (onConnectError != null) {
                        onConnectError.run();
                    }
                })
                .subscribe();
    }
}
