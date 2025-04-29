SHELL := bash

ifeq ($(OS),Windows_NT)
	win_shell = powershell
    activate_venv = source .venv/Scripts/activate
    zip_command = jar -cfM
else
	win_shell = 
    activate_venv = source .venv/bin/activate
    zip_command = zip -r
endif

webapp_name ?= app-service-321bc

n ?= 50
ifdef name
	test_query_str = ?name=$(name)
else
	test_query_str =
endif
test_request_url = https://$(webapp_name).azurewebsites.net/say_hello$(test_query_str)

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	$(win_shell) python -m venv .venv
	$(activate_venv) && pip install -r app_code/requirements.txt

infra/.tfvars:
	@printf 'azure_subscription_id=$(shell az account show --query "id")\n' > infra/.tfvars

.PHONY: tf-init tf-plan tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra init

# Usage: make tf-plan
tf-plan: infra/.tfvars
	terraform -chdir=infra plan -var-file=.tfvars -var="webapp_name=$(webapp_name)"

# Usage: make tf-apply
tf-apply: infra/.tfvars
	terraform -chdir=infra apply -var-file=.tfvars -var="webapp_name=$(webapp_name)"

# Usage: make tf-destroy
tf-destroy: infra/.tfvars
	terraform -chdir=infra destroy -var-file=.tfvars -var="webapp_name=$(webapp_name)"

.PHONY: zip

# Usage: make zip
zip:
	cd app_code && $(zip_command) ../app.zip .

.PHONY: deploy

# Usage: make deploy
deploy: zip
	az webapp deploy --name $(webapp_name) \
					 --resource-group $(webapp_name)-rg \
					 --type zip \
					 --src-path app.zip \
					 --timeout 900000

.PHONY: send-test-request send-test-requests

# Usage: make send-test-request [name=xyz]
send-test-request:
	curl $(test_request_url)

# Usage: make send-test-requests [n=50] [name=xyz]
send-test-requests:
	for i in {1..$(n)}; do curl $(test_request_url); done
