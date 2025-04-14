SHELL := bash

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	python -m venv .venv
	source .venv/bin/activate && pip install -r app_code/requirements.txt
	@printf "Make sure to run: \n\t\t\t source .venv/bin/activate \n\n"

.PHONY: zip

# Usage: make zip
zip:
	cd app_code && zip -r ../app.zip .

.PHONY: tf-init tf-plan tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra init -var-file=.tfvars

# Usage: make tf-apply
tf-apply:
	terraform -chdir=infra apply -var-file=.tfvars

# Usage: make tf-destroy
tf-destroy:
	terraform -chdir=infra destroy -var-file=.tfvars

.PHONY: deploy

# Usage: make deploy
deploy:
	az webapp deploy --resource-group app-service-tf-rg --name app-service-343nde --type zip --src-path app.zip