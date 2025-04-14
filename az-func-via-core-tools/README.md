# Azure Functions - Example in Python

https://learn.microsoft.com/en-us/azure/azure-functions/create-first-function-cli-python

### Create venv

```commandline
make .env
```

### To start locally

```commandline
make run-locally
```

### To create infra in Azure

```commandline
make create-infra
```

### To deploy to Azure

```commandline
make deploy
```

To clean up resources:
```commandline
az group delete --name azure-python-demo-func-7-rg
```
