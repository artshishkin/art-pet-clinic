#ART Pet Clinic

##SFG Tutorial: Spring 5

###49 Using Maven Release Plugin
- added `<scm>` block to `pom.xml`
- modified `<configuration>` of maven-release-plugin to run `install` (not deploy)
- set `<autoVersionSubmodules>true</autoVersionSubmodules>` 
- ...
- removed configuration to try `mvn release:perform -Darguments="-Dmaven.deploy.skip=true"` 
