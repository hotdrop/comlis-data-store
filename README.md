# colist-data-store
Webから取得したデータをコムリスへ送るためのストアアプリ。  
API設計やサーバーサイドkotlinの勉強を兼ねているので良いコードか疑問。

# Environment
* Spring Boot + Kotlin + Redis
* Redisはdockerコンテナ（image`redis`）を使用
* Redisとの接続はjedisを使用
* APIのテストはSwagger-uiで行なっております。

# Usage
使い方です。まだ発展途上なので適宜更新していきます。

## dockerコンテナのRedisサーバー起動
Redisサーバーはdockerコンテナ上で起動させています。  
dockerイメージは`redis`を使用しています。  
なお、`docker run`時に`-p 3000:6379`を指定しています。この3000ポートは特に意味はありません。  
現在はコード中にRedisサーバーへの接続情報を直書きしていますので変更する場合は合わせてコードも修正する必要があります。  

## Spring Boot起動
Spring Bootのルートディレクトリで以下のコマンドを実行します。
```command
./gradlew bootRun
```
起動に成功したら以下のようなメッセージが出力されます。
> Started ApplicationKt in XXX seconds (JVM running for ...)

## Swagger-ui表示API検証
ブラウザで以下にアクセスします。
```url
http://localhost:8080/swagger-ui.html
```
このUI上でSwaggerでAPIの検証をします。
