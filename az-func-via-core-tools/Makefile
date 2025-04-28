SHELL := bash

ifeq ($(OS),Windows_NT)
    activate_venv = source .venv/Scripts/activate
else
    activate_venv = source .venv/bin/activate
endif

location ?= westeurope
function_app ?= say-hello-func-7
resource_group ?= $(function_app)-rg
logs_workspace ?= $(function_app)-logs
app_insights ?= $(function_app)-insights
storage_account ?= $(subst -,,$(function_app))storage

n ?= 50
ifdef name
	test_query_str = ?name=$(name)
else
	test_query_str =
endif

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	python -m venv .venv
	$(activate_venv) && pip install -r requirements.txt

.PHONY: func-init

# Usage: make func-init
func-init:
	$(activate_venv) && func init

.PHONY: func-start

# Usage: make func-start
func-start:
	$(activate_venv) && func start

.PHONY: create-infra

# Usage: make create-infra
create-infra:
	az group create --name $(resource_group) \
					--location $(location)
	az storage account create --name $(storage_account) \
							  --resource-group $(resource_group) \
							  --sku Standard_LRS
	az monitor log-analytics workspace create --name $(logs_workspace) \
											  --resource-group $(resource_group) \
											  --sku PerGB2018
	az monitor app-insights component create --app $(app_insights) \
											 --location $(location) \
											 --resource-group $(resource_group) \
											 --workspace $(logs_workspace)
	az functionapp create --name $(function_app) \
						  --resource-group $(resource_group) \
						  --consumption-plan-location $(location) \
						  --storage-account $(storage_account) \
						  --app-insights $(app_insights) \
						  --runtime python \
						  --runtime-version 3.13 \
						  --functions-version 4 \
						  --os-type linux
	az monitor diagnostic-settings create --name $(function_app)-logs \
										  --resource $(function_app) \
										  --resource-group $(resource_group) \
										  --resource-type "Microsoft.Web/sites" \
										  --workspace $(logs_workspace) \
										  --logs "[{category:FunctionAppLogs,enabled:true,retention-policy:{enabled:false,days:0}}]"
.PHONY: deploy

# Usage: make deploy
deploy:
	func azure functionapp publish $(function_app)

.PHONY: send-test-request send-test-requests

# Usage: make send-test-request [name=xyz]
send-test-request:
	curl https://$(function_app).azurewebsites.net/api/say_hello$(test_query_str)

# Usage: make send-test-requests [n=50] [name=xyz]
send-test-requests:
	for i in {1..$(n)}; do curl https://$(function_app).azurewebsites.net/api/say_hello$(test_query_str); done

.PHONY: destroy-infra

# Usage: make destroy-infra
destroy-infra:
	az group delete --name $(resource_group)

