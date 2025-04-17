#!/bin/bash

for userId in 11111 22222
do
  for eventType in AccountCreated AccountUpdated AccountDeleted
  do
    curl -X PUT http://localhost:8080/v1/events \
      -H "Content-Type: application/json" \
      -d "[{\"eventId\": \"$(uuid -v 4)\", \"userId\": $userId, \"eventType\": \"$eventType\"}]"
  done
done