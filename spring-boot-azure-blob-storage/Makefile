.PHONY: build

build:
	./gradlew -x test clean build

.PHONY: tf-init tf-apply

tf-init:
	terraform -chdir=infra/azure init -var-file=.tfvars

tf-apply:
	terraform -chdir=infra/azure apply -var-file=.tfvars

tf-destroy:
	terraform -chdir=infra/azure destroy -var-file=.tfvars

.PHONY: ansible-deploy

ansible-deploy:
	ansible-playbook -i infra/ansible/inventory.ini infra/ansible/deploy-to-host.yaml