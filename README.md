# comlis-data-store
Webから取得したデータをコムリスへ送るためのストアアプリ。  
API設計やサーバーサイドkotlinの勉強を兼ねているので良いコードか疑問。

# Environment
* Spring Boot v1.5.4.RELEASE
* Kotlin v1.1.4−3
* Redis v4.0.1
* docker for mac
* docker-compose
> Spring BootとRedisはそれぞれ別々のdockerコンテナで起動します。  
使用しているイメージ等はdockerディレクトリのdocker-compose.ymlを確認ください。
APIのテストはSwagger-uiで行なっております。

# Usage
使い方です。

## Step1
本アプリはjarファイルを実行します。jarファイル置き場を適当に決めます。
また、Redisのdbファイルの置き場所も決めます。
決めたら`docker/docker-compose.yml`の`volumes`を修正します。

## Step2
gradlewのbuildを実行し、アプリのjarファイルを作成します。
```command
./gradlew build
```
build/libsディレクトリに`comlis-rest-service-0.1.0.jar`が出来ているはずです。
それをStep1で決めたディレクトリにコピーします。

## Step3
`docker-compose.yml`が置いてあるディレクトリに移動し、以下のコマンドを実行します。
```command
docker-compose up
```

## Step4
Redis及びSpring-bootの起動ができたらブラウザで以下にアクセスします。
```url
http://localhost:8080/swagger-ui.html
```
このUI上でSwaggerでAPIの検証をします。
