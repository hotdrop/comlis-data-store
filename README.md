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
    - [your work directory]:/data  <- modified
spring-boot:
  volumes:
    - [your work directory]:/app  <- modified
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
