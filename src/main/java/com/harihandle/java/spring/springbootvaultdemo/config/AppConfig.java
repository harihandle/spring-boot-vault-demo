package com.harihandle.java.spring.springbootvaultdemo.config;

import com.harihandle.java.spring.springbootvaultdemo.model.MyCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.VaultException;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultToken;

import java.net.URI;

@Configuration
public class AppConfig {

    @Bean
    public MyCredentials myCredentials(VaultOperations vaultOperations) {
        return vaultOperations.read("secret/spring-boot-vault-demo/mycredentials", MyCredentials.class).getData();
    }

    // This method is needed only if not using vault bootstrap
    @Bean
    public VaultEndpoint vaultEndpoint(
            @Value("${spring.cloud.vault.uri}") String vaultUri
    ) {
        return VaultEndpoint.from(URI.create(vaultUri));
    }

    // This method is needed only if not using vault bootstrap
    @Bean
    public ClientAuthentication clientAuthentication(
            @Value("${spring.cloud.vault.token}") String token
    ) {
        return new ClientAuthentication() {
            @Override
            public VaultToken login() throws VaultException {
                return VaultToken.of(token);
            }
        };
    }
}
