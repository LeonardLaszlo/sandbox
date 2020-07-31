#!/bin/bash

./gradlew clean bootJar &&
java -jar build/libs/spring-boot-sandbox-*.jar
