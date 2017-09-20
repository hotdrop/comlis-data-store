# comlis-data-store
This application is provides a REST API for save and retrieving company data.  
Also, This application also serves as a study of server side kotlin.

# Environment
- Kotlin v1.1.4−3
- Spring Boot v1.5.7.RELEASE
- Redis v4.0.1

# About using docker
This application works with two docker containers.  
These are managed by docker-compose.  
Please read the `docker/docker-compose.yml` for details.

# Simple Run Example
Here is an example using `docker for mac` in macOS Sierra.

## Step1
Modified the `volumes` of the `docker/docker-compose.yml`.
  - redis volumes: can be empty. `dump.rdb` is generated later.
  - spring-boot volumes: into comlis-rest-service.jar(details Step2)
```docker-compose.yml
redis:
  volumes:
    - ~/Desktop/MyStudy/dockerShare/comlis-store:/data  <- modified
spring-boot:
  volumes:
    - ~/Desktop/MyStudy/dockerShare/comlis-store:/app  <- modified
```

## Step2
Copy the jar file of this application to the directory specified by spring-boot volumes.  
The jar file to be copied is in `./build/libs/comlis-store.jar`

## Step3
Change current directory where `docker-compose.yml` is located and run docker containers.
```command
cd ./docker
docker-compose up
```

## Step4
After starting the containers, access the following in the browser and test the API.
```url
http://localhost:8080/swagger-ui.html
```
