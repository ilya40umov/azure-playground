# App deployed via Terraform and Ansible to Azure VM

App is written using Spring Boot and is working with Azure Blob Storage

### Commands

#### To initialize terraform

```shell
make tf-init
```

#### To apply create/update infra

```shell
make tf-apply
```

#### To build the jar file

```shell
make build
```

#### To deploy the jar file to VM

```shell
make deploy
```

#### To destroy infra

```shell
make tf-destroy
``` 
