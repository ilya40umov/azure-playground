# App service app (Python, Flask) deployed via Terraform

### Commands

#### Create venv

```commandline
make venv
```

#### To initialize terraform

```shell
make tf-init
```

#### To create/update infra

```shell
make tf-apply
```

#### To package the app

```shell
make zip
```

#### To deploy the app

```shell
make deploy
```

#### To destroy infra

```shell
make tf-destroy
``` 
