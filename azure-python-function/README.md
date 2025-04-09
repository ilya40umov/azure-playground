# Azure Functions - Example in Python

https://learn.microsoft.com/en-us/azure/azure-functions/create-first-function-cli-python

### Create venv

```commandline
python -m venv .venv
source .venv/bin/activate
```

### To start locally

```commandline
func start
curl http://localhost:7071/api/AzurePythonDemoFunc
```

### To deploy to Azure

```commandline
az group create --name azure-python-demo-func-7-rg --location westeurope
az storage account create --name azure00func7storage --location westeurope --resource-group azure-python-demo-func-7-rg --sku Standard_LRS
az functionapp create --resource-group azure-python-demo-func-7-rg --consumption-plan-location westeurope --runtime python --runtime-version 3.12 --functions-version 4 --name azure-python-demo-func-7 --os-type linux --storage-account azure00func7storage
func azure functionapp publish azure-python-demo-func-7
curl https://azure-python-demo-func-7.azurewebsites.net/api/azurepythondemofunc
```

To clean up resources:
```commandline
az group delete --name azure-python-demo-func-7-rg
```