# spring-boot-vault-demo
Refer official spring boot and vault documentation for a more updated way.

## Vault
The defacto way for holding secrets

- **Vault Server** - holds the secrets and comes with a UI
- **Vault CLI** - Connect to vault and CRUD secrets

### Install Vault
- https://www.vaultproject.io/downloads.html
- Add to path
- `chmod 755 vault`

### Run vault locally
`vault server --dev --dev-root-token-id="00000000-0000-0000-0000-000000000000"`

### Connect to vault
```
export VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
export VAULT_ADDR="http://127.0.0.1:8200"
```

### Add secrets
#### UI
- http://127.0.0.1:8200/ui
- secrets/spring-boot-vault-demo/mycredentials
    ```
    {
      "others": {
        "details": {
          "height": "160 cms",
          "weight": "60 Kgs"
        },
        "role": "ADMIN"
      },
      "password": "hari123",
      "username": "hari"
    }
    ```
#### CLI
```
vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
vault kv put secret/gs-vault-config/cloud example.username=clouduser example.password=cloudpassword
```

### Troubleshooting
- NullPointerException while reading from path
    - https://stackoverflow.com/a/55433475

### Issues
None

### Easy Way
- Include vault core dependency
- Extend `AbstractVaultConfiguration` and implement the methods.
    - This will get you `VaultOperations`
- Have a `VaultRepository` configured with base path (`secret/spring-boot-vault-demo`) and read secret from subpaths (`mycredentials`)
```
// Autowire VaultOperations. Lets you read from multiple different base paths.
VaultKeyValueOperations vaultKeyValueOperations = vaultOperations.opsForKeyValue("secret/spring-boot-vault-demo", VaultKeyValueOperationsSupport.KeyValueBackend.unversioned());
VaultResponseSupport<T> result = vaultKeyValueOperations.get("mycredentials", MyCredentials.class);
MyCredentials myCredentials = result.getData();
```