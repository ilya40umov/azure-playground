# Kafka consumer/producer apps deployed via Pulumi

###  What was used
* Event Hubs (as a managed Kafka)
* Key Vault
* ACR
* AKS
* Workload Identity
* Spring Boot
* Pulumi

### Commands

#### List all commands

```shell
make help
```

#### Set subscription ID in Pulumi

```shell
make pulumi-set-subscription-id subscriptionId=xyz
```

#### Set up infra via Pulumi

```shell
make pulumi-up
```

#### Build and push images to ACR

```shell
make push-to-acr
```

#### Apply Kubernetes definitions

```shell
make k8s-apply
```

#### Destroy the infra via Pulumi

```shell
make pulumi-down
```

### Other notes

#### Login via Azure CLI in an AKS pod via a federated token

```shell
az login --federated-token "$(cat $AZURE_FEDERATED_TOKEN_FILE)" --service-principal -u $AZURE_CLIENT_ID -t $AZURE_TENANT_ID
```