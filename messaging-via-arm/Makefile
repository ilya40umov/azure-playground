SHELL = /bin/bash

TMP_FILE_NAME := $(shell uuidgen | tr -d '-').txt

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: create-rg create-storage create-bus create-subs create-infra

# Usage: make create-rg
create-rg:
	az deployment sub create --location westeurope --template-file infra/resource-group.json --parameters infra/resource-group.params.json

# Usage: make create-storage
create-storage:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/storage-account.json --parameters infra/storage-account.params.json --parameters currentUserObjectId=$(shell az ad signed-in-user show --query id -o tsv)

# Usage: make create-bus
create-bus:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/service-bus.json --parameters infra/service-bus.params.json --parameters currentUserObjectId=$(shell az ad signed-in-user show --query id -o tsv)

# Usage: make create-subs
create-subs:
	az deployment group create --resource-group messaging-via-arm-rg --template-file infra/event-grid.json --parameters infra/event-grid.params.json

# Usage: make create-infra
create-infra: create-rg create-storage create-bus create-subs

.PHONY: upload-file

# Usage: make upload-file
upload-file:
	echo "Yo yo!" > /tmp/$(TMP_FILE_NAME)
	az storage blob upload --auth-mode login --account-name "messagingviaarm"  --container-name "uploads" --name $(TMP_FILE_NAME) --file /tmp/$(TMP_FILE_NAME)
	rm /tmp/$(TMP_FILE_NAME)
