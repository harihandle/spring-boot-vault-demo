package com.harihandle.java.spring.springbootvaultdemo.config;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.ClientAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.config.AbstractVaultConfiguration;
import org.springframework.vault.support.VaultToken;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class VaultConfig extends AbstractVaultConfiguration {

    @Value("${spring.cloud.vault.uri}")
    private String vaultUri;

    @Value("${spring.cloud.vault.token}")
    private String token;

    @Bean
    public ClientAuthentication clientAuthentication() {
        return () -> VaultToken.of(token);
    }

    @Override
    public VaultEndpoint vaultEndpoint() {
        return VaultEndpoint.from(getVaultURI());
    }

    private URI getVaultURI() {
        try {
            return new URI(vaultUri);
        } catch (URISyntaxException e) {
            throw new BeanInitializationException("Invalid vault uri", e);
        }
    }
}
