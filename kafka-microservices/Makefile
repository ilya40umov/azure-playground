SHELL:=/bin/bash

.PHONY: build rebuild

build:
	./gradlew -x test build

rebuild: build

.PHONY: send-test-events

send-test-events:
	./bin/send-test-events.sh