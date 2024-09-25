package com.cocot3ro.gestionhotel.modulo_almacen.desktop.client;

import com.cocot3ro.gestionhotel.modulo_almacen.desktop.model.AlmacenModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IAlmacenClient extends IRSocketClient {

    Flux<AlmacenModel[]> getAlmacenEntries();

    Mono<AlmacenModel> saveAlmacenEntry(AlmacenModel item);

    Mono<AlmacenModel> updateAlmacenEntry(AlmacenModel item);

    void deleteAlmacenEntry(AlmacenModel item);
}
