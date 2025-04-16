# Azure Functions (in Python) deployed via Core Tools

### Commands

#### Create venv

```commandline
make venv
```

#### To start locally

```commandline
make run-locally
```

#### To create infra in Azure

```commandline
make create-infra
```

#### To deploy to Azure

```commandline
make deploy
```

#### To clean up resources:
```commandline
az group delete --name azure-python-demo-func-7-rg
```
