package com.cocot3ro.gestionhotel.modulo_almacen_desktop.config;

import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.IAlmacenClient;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.ICoreClient;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.rsclient.RSocketCoreClient;
import com.cocot3ro.gestionhotel.modulo_almacen_desktop.client.rsclient.RSocketIAlmacenClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
public class ClientConfiguration {

    @Bean
    public IAlmacenClient rSocketClient(RSocketRequester rSocketRequester) {
        return new RSocketIAlmacenClient(rSocketRequester);
    }

    @Bean
    ICoreClient coreClient(RSocketRequester rSocketRequester) {
        return new RSocketCoreClient(rSocketRequester);
    }

    @Bean
    public RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder.tcp("localhost", 7000);
    }
}
