SHELL = /bin/bash

TMP_FILE_NAME := $(shell uuidgen | tr -d '-').txt

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: build build-image deploy

# Usage: make build
build:
	./gradlew -x test build

# Usage: make build-image
build-image:
	./gradlew bootBuildImage --imageName=messaging-via-arm:latest

# Usage: make deploy
deploy: build-image
	AZURE_CLI_DISABLE_CONNECTION_VERIFICATION=1 az acr login --name messagingviaarmacr
	docker tag messaging-via-arm:latest messagingviaarmacr.azurecr.io/messaging-via-arm:latest
	docker push messagingviaarmacr.azurecr.io/messaging-via-arm:latest

.PHONY: setup-rg setup-storage setup-bus setup-subs setup-env setup-app setup-infra

# Usage: make setup-rg
setup-rg:
	az deployment sub create --location westeurope --template-file infra/resource-group.json --parameters infra/resource-group.params.json

# Usage: make setup-storage
setup-storage:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/storage-account.json --parameters infra/storage-account.params.json --parameters currentUserObjectId=$(shell az ad signed-in-user show --query id -o tsv)

# Usage: make setup-bus
setup-bus:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/service-bus.json --parameters infra/service-bus.params.json --parameters currentUserObjectId=$(shell az ad signed-in-user show --query id -o tsv)

# Usage: make setup-subs
setup-subs:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/event-grid.json --parameters infra/event-grid.params.json

# Usage: make setup-env
setup-env:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/container-env.json --parameters infra/container-env.params.json

# Usage: make setup-app
setup-app:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/container-app.json --parameters infra/container-app.params.json

# Usage: make setup-infra
setup-infra: setup-rg setup-storage setup-bus setup-subs setup-env

# Usage: make setup-infra-and-deploy
setup-infra-and-deploy: setup-rg setup-storage setup-bus setup-subs setup-env deploy setup-app

.PHONY: upload-file

# Usage: make upload-file
upload-file:
	echo "Yo yo!" > /tmp/$(TMP_FILE_NAME)
	az storage blob upload --auth-mode login --account-name "messagingviaarm"  --container-name "uploads" --name $(TMP_FILE_NAME) --file /tmp/$(TMP_FILE_NAME)
	rm /tmp/$(TMP_FILE_NAME)
