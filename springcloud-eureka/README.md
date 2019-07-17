# Eureka注册中心
### 部署
```
mvn clean package -Dmaven.test.skip=true
```
build 成功后，可以在target下找到j ar包
```$xslt
ls -al target
```
启动
```
nohup java -jar target/springcloud-eureka-0.0.1-SNAPSHOT.jar > out 2>&1 & 
```
####高可用
启动两个，互相监听。

应用在任意一个eureka server上注册，就会被同步到整个eureka集群。
```$xslt
java -jar target/springcloud-eureka-0.0.1-SNAPSHOT.jar -Dserver.port=8762 -Deureka.client.service-url.defaultZone=http://localhost:8761/eureka/
java -jar target/springcloud-eureka-0.0.1-SNAPSHOT.jar -Dserver.port=8761 -Deureka.client.service-url.defaultZone=http://localhost:8762/eureka/
```
为保持高可用，eureka client也应注册到所有eureka server
```
java -jar target/springcloud-eureka-client-0.0.1-SNAPSHOT.jar -Dserver.port=8800 -Deureka.client.service-url.defaultZone=http://localhost:8761/eureka/,http://localhost:8762/eureka/
``` 