SHELL:=/bin/bash

.PHONY: help

# Usage: make help
help:
	@cat Makefile | grep "^# Usage:"

.PHONY: build rebuild

# Usage: make build
build:
	./gradlew -x test build

rebuild: build

# Usage: make up
.PHONY: up

up:
	docker compose up -d

# Usage: make list-topics
.PHONY: list-topics

list-topics:
	docker exec --workdir /opt/kafka/bin -it broker ./kafka-topics.sh --list --bootstrap-server=localhost:9092

# Usage: make describe-topic name=xyz
describe-topic:
	docker exec --workdir /opt/kafka/bin -it broker ./kafka-topics.sh --describe --topic $(name) --bootstrap-server=localhost:9092

.PHONY: send-test-events

# Usage: make send-test-events
send-test-events:
	./bin/send-test-events.sh

.PHONY: pulumi-up pulumi-down

# Usage: make pulumi-set-subscription-id subscriptionId=xyz
pulumi-set-subscription-id:
	pulumi config set --cwd infra/ --stack main --secret azure:subscriptionId $(subscriptionId)

# Usage: make pulumi-up
pulumi-up:
	pulumi up --cwd infra/ --stack main

# Usage: make pulumi-down
pulumi-down:
	pulumi down --cwd infra/ --stack main