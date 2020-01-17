package com.harihandle.java.spring.springbootvaultdemo.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.vault.core.VaultKeyValueOperations;
import org.springframework.vault.core.VaultKeyValueOperationsSupport;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.VaultResponseSupport;

@Repository
public class VaultRepository {

    private final VaultKeyValueOperations vaultKeyValueOperations;

    @Autowired
    public VaultRepository(
            VaultOperations vaultOperations,
            @Value("${vault.base.path}") String vaultBasePath
    ) {
        vaultKeyValueOperations = vaultOperations
                .opsForKeyValue(vaultBasePath, VaultKeyValueOperationsSupport.KeyValueBackend.unversioned());
    }

    public <T> T findSecretBySubPath(String subPath, Class<T> clazz) {
        VaultResponseSupport<T> result =
                vaultKeyValueOperations.get(subPath, clazz);
        if (result != null) {
            return result.getData();
        }
        return null;
    }
}
