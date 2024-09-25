package com.cocot3ro.gestionhotel.modulo_almacen.desktop.client;

import reactor.core.publisher.Mono;

public interface ICoreClient extends IRSocketClient {

    Mono<String> checkConnection();
}
