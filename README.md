
# Setup kafka with docker
`docker-compose -f kafka-docker-compose.yml up -d`

* kafka-ui lets us visualize our cluster state in the browser on port 8080
* create new topic: getting-started

# Build + Package Scala Project
run maven command `mvn clean package`


# Run jar on linux
``` 
java -jar target/demo-1.0.jar
```
