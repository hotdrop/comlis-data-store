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
```docker-compose.yml
redis:
  volumes:
    - [your work directory]:/data  <- modified
spring-boot:
  volumes:
    - [your work directory]:/app  <- modified
```
  - redis.volumes  
    Can be empty. This volumes generated `dump.rdb` after running redis on docker.
  - spring-boot.volumes  
    Into `env/server.keystore`(details Step2) and `comlis-store.jar`(details Step4)

## Step2
This application using https. Therefore, you need to prepare `server.keystore`.  
:memo: What keystore file is that pkcs12(together key and crt) file is registered to Java key Store.  

## Step3
Modified XXXX in `application.yml` of the `src/main/resources`.  
Default port number: Redis is 6379, Spring-boot is 8080.:-1: But not good default port.

## Step4
Make jar file of this application and copy to the directory specified by spring-boot volumes.  
How to make a jar file is `./gradlew build`.  
After executing command successfully, created jar file to `build/libs/`.

## Step5
Change current directory where `docker-compose.yml` is located and run docker containers.
```command
> cd ./docker
> docker-compose up

or

> docker-compose -f ./docker/docker-compose.yml up
```

## Step6
After starting the docker containers, access the following in the browser and test the API.
```url
https://localhost:XXXX/swagger-ui.html
※ XXXX: Please rewrite the port number of application.yml you set in Step3.
```

# API
## 1. /scraping/lastItemKey  

| No |Method|param| body | Notes |
|:--:|:----:|:---:|:----:|:----- |
| 1  |POST  |  -  |String| -   |
| 2  |GET   |  -  |String| When there is no data: status = NO_CONTENT |
| 3  |DELETE| Int |  -   | It can not be deleted unless the Number of this parameter matches the one in the code for verification. |

## 2. /companies

| No |Method|param|       body      | Notes |
|:--:|:----:|:---:|:---------------:|:----- |
| 1  |POST  |  -  |JSON type:Company| When ID or Name in JSON Data are null or empty = status:Bad request |
| 2  |GET   |fromDateEpoch |JSON type:Company| When there is no data = status:NO_CONTENT <BR><BR> fromDateEpoch acquires subsequent company for company.dateEpoch.I <BR> If dateEpoch is null or not number, it can not be acquired with API. |
