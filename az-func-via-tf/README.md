# Azure Functions (Python) deployed via Terraform

### Commands

#### Create venv

```commandline
make venv
```

#### To package the function

```shell
make zip
```

#### To initialize terraform

```shell
make tf-init
```

#### To apply create/update infra

```shell
make tf-apply
```

#### To destroy infra

```shell
make tf-destroy
``` 
