package com.practice.shell.config;

import java.net.http.HttpClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestClientConfig {
    
    /*
     * Once built, an HttpClient can be used to send multiple requests. Because HttpClient is immutable
     */
    @Bean
    public HttpClient httpClient() {
        /*
         * HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .followRedirects(Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(20))
                .proxy(ProxySelector.of(new InetSocketAddress("proxy.example.com", 80)))
                .authenticator(Authenticator.getDefault())
                .build();
         */
        return HttpClient.newHttpClient();
    }
}
