SHELL := bash

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: venv

# Usage: make venv
venv:
	python -m venv .venv
	source .venv/bin/activate && pip install -r requirements.txt
	@printf "Make sure to run: \n\t\t\t source .venv/bin/activate \n\n"

.PHONY: zip

# Usage: make zip
zip:
	mkdir -p build
	cp requirements.txt build/
	cp -r func_code/* build/
	cd build && zip -r ../function.zip .
	rm -rf build

.PHONY: tf-init tf-plan tf-apply tf-destroy

# Usage: make tf-init
tf-init:
	terraform -chdir=infra init -var-file=.tfvars

# Usage: make tf-apply
tf-apply:
	terraform -chdir=infra apply -var-file=.tfvars

# Usage: make tf-plan
tf-plan:
	terraform -chdir=infra plan -var-file=.tfvars

# Usage: make tf-destroy
tf-destroy:
	terraform -chdir=infra destroy -var-file=.tfvars

