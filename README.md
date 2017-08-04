# colist-data-store
Webから取得したデータをコムリスへ送るためのストアアプリ。
API設計やサーバーサイドkotlinの勉強を兼ねているので良いコードか疑問。

# Environment
* Spring Boot + Kotlin + Redis
* Redisはdockerコンテナ（image`redis`）を使用
* Redisとの接続はjedisを使用
* APIのテストはSwagger-uiで行なっております。

# Usage
## dockerイメージ`redis`を使用して起動します。  
なお、docker run時に`-p 3000:6379`を指定しています。この3000ポートは特に意味はありません。  
現在はコード中にRedisサーバーへの接続情報を直書きしていますので変更する場合は合わせてコードも修正する必要があります。  

## Spring Bootを起動します。
Spring Bootのルートディレクトリで以下のコマンドを実行します。
```command
./gradlew bootRun
```
起動に成功したら以下のようなメッセージが出力されます。
> Started ApplicationKt in XXX seconds (JVM running for ...)

## Swagger-uiを表示します。
ブラウザで以下にアクセスし、SwaggerでAPIのテストをします。
```url
http://localhost:8080/swagger-ui.html#/
```
