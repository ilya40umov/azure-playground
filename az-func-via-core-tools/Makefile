SHELL := bash

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

# Usage: make .env
.env:
	python -m venv .venv
	@printf "Make sure to run: \n\t\t\t source .venv/bin/activate"

.PHONY: run-locally

# Usage: make run-locally
run-locally:
	@printf "To test function run: \n\t\t\t curl http://localhost:7071/api/AzurePythonDemoFunc"
	source .venv/bin/activate && func start

.PHONY: make create-infra

# Usage: make create-infra
create-infra:
	az group create --name azure-python-demo-func-7-rg --location westeurope
	az storage account create --name azure00func7storage --location westeurope --resource-group azure-python-demo-func-7-rg --sku Standard_LRS
	az functionapp create --resource-group azure-python-demo-func-7-rg --consumption-plan-location westeurope --runtime python --runtime-version 3.12 --functions-version 4 --name azure-python-demo-func-7 --os-type linux --storage-account azure00func7storage

.PHONY: destroy-infra

# Usage: make destroy-infra
destroy-infra:
	az group delete --name azure-python-demo-func-7-rg

.PHONY: deploy

# Usage: make deploy
deploy:
	func azure functionapp publish azure-python-demo-func-7	
	@printf "To test run: \n\t\t\t curl https://azure-python-demo-func-7.azurewebsites.net/api/azurepythondemofunc"
