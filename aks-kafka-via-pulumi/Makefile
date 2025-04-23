SHELL:=bash

AZ_INFRA_DIR:=az-infra
K8S_INFRA_DIR:=k8s-infra
EXPORT_ENV_VARS:=set -o allexport && source k8s-infra/.env && set +o allexport

HOST?=localhost

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: local-up local-down

# Usage: make local-up
local-up:
	docker compose up -d

# Usage: make local-down
local-down:
	docker compose down -v

.PHONY: local-list-topics local-describe-topic

# Usage: make local-list-topics
local-list-topics:
	docker exec --workdir /opt/kafka/bin -it broker ./kafka-topics.sh --list --bootstrap-server=localhost:9092

# Usage: make local-describe-topic name=xyz
local-describe-topic:
	docker exec --workdir /opt/kafka/bin -it broker ./kafka-topics.sh --describe --topic $(name) --bootstrap-server=localhost:9092

.PHONY: send-test-events

# Usage: make send-test-events [host=localhost]
send-test-events:
	./bin/send-test-events.sh $(host)

.PHONY: pulumi-set-subscription-id pulumi-up pulumi-refresh pulumi-down

# Usage: make pulumi-set-subscription-id subscriptionId=xyz
pulumi-set-subscription-id:
	pulumi config set --cwd $(AZ_INFRA_DIR) --stack main --secret azure:subscriptionId $(subscriptionId)

# Usage: make pulumi-up
pulumi-up:
	pulumi up --cwd $(AZ_INFRA_DIR) --stack main
	pulumi stack output ACR_LOGIN_SERVER --cwd $(AZ_INFRA_DIR) --stack main --shell > $(K8S_INFRA_DIR)/.env
	pulumi stack output WORKLOAD_CLIENT_ID --cwd $(AZ_INFRA_DIR) --stack main --shell >> $(K8S_INFRA_DIR)/.env

# Usage: make pulumi-refresh
pulumi-refresh:
	pulumi refresh --cwd $(AZ_INFRA_DIR) --stack main

# Usage: make pulumi-down
pulumi-down:
	pulumi down --cwd infra/ --stack main

.PHONY: aks-get-credentials

# Usage: make aks-get-credentials cluster-name=xyz
aks-get-credentials:
	az aks get-credentials --resource-group demo4kafka-rg --name $(cluster-name)

.PHONY: build-images push-to-acr

# Usage: make build-images
build-images:
	./gradlew kafka-producer:bootBuildImage --imageName=kafka-producer:latest
	./gradlew kafka-consumer:bootBuildImage --imageName=kafka-consumer:latest

# Usage: make push-to-acr
push-to-acr: build-images
	$(EXPORT_ENV_VARS) && AZURE_CLI_DISABLE_CONNECTION_VERIFICATION=1 az acr login --name $${ACR_LOGIN_SERVER}
	$(EXPORT_ENV_VARS) && docker tag kafka-producer:latest $${ACR_LOGIN_SERVER}/kafka-producer:latest
	$(EXPORT_ENV_VARS) && docker tag kafka-consumer:latest $${ACR_LOGIN_SERVER}/kafka-consumer:latest
	$(EXPORT_ENV_VARS) && docker push $${ACR_LOGIN_SERVER}/kafka-producer:latest
	$(EXPORT_ENV_VARS) && docker push $${ACR_LOGIN_SERVER}/kafka-consumer:latest

.PHONY: k8s-apply

# Usage: make k8s-apply
k8s-apply:
	$(EXPORT_ENV_VARS) && envsubst < $(K8S_INFRA_DIR)/service-account.yaml | kubectl apply -f -
	$(EXPORT_ENV_VARS) && envsubst < $(K8S_INFRA_DIR)/kafka-producer.yaml | kubectl apply -f -
	$(EXPORT_ENV_VARS) && envsubst < $(K8S_INFRA_DIR)/kafka-consumer.yaml | kubectl apply -f -
	@#kubectl apply -f $(K8S_INFRA_DIR)/ubuntu-pod.yaml