# spring-boot-vault-demo
- Refer official spring boot and vault documentation for a more updated way.
- Refer no-bootstrap branch for a different way

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
- `spring.cloud.vault.enabled=false` has no effect. It doesn't disable vault bootstrap configuration.

### Easy Way
- Include Spring cloud and vault dependencies
- **bootstrap.yml** in resources folder
    ```
    spring.cloud.vault:
      host: localhost
      port: 8200
      scheme: http
      uri: http://localhost:8200
      connection-timeout: 5000
      read-timeout: 15000
      config:
        order: -10
      token: 00000000-0000-0000-0000-000000000000
    ```
- POJO bean
    ```
    @Bean
    public MyCredentials myCredentials(VaultOperations vaultOperations) {
        return vaultOperations.read("secret/spring-boot-vault-demo/mycredentials", MyCredentials.class).getData();
    }
    ```
