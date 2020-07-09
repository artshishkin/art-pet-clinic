[![CircleCI](https://circleci.com/gh/artshishkin/art-pet-clinic.svg?style=svg)](https://circleci.com/gh/artshishkin/art-pet-clinic)

#ART Pet Clinic

##SFG Tutorial: Spring 5

###49 Using Maven Release Plugin
- added `<scm>` block to `pom.xml`
- modified `<configuration>` of maven-release-plugin to run `install` (not deploy)
- set `<autoVersionSubmodules>true</autoVersionSubmodules>` 
- ...
- removed configuration to try `mvn release:perform -Darguments="-Dmaven.deploy.skip=true"` 

###77 Spring Boot Configuration Demo

####Useful commands:

- `mvn spring-boot:help -Ddetail=true`
- `mvn spring-boot:run -Dspring-boot.run.arguments=--debug`???-not working for me
- `mvn spring-boot:run -Drun.arguments=--debug`???-not working for me
- `mvn spring-boot:run --debug` -some mystic debug output
- you can in IDEA toggle radio button to output debug logging!!! in "Run Configuration" 

###183 Accept-Language: ru-RU,ru (in Postman)
