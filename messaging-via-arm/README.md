# Messaging app deployed via ARM

### What was used
* Blob Storage
* Event Grid System Topic
* Storage Queue
* Service Bus Queue
* Container App
* Spring Boot

### Commands

#### To set up most of the infra

```shell
make setup-infra
```

#### To build and deploy image to ACR

```shell
make deploy
```

#### To deploy the app to Container Apps

```shell
make setup-app
```

#### To upload a file to blob storage (triggers notifications)

```shell
make upload-file
```