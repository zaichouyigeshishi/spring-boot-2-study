# mqtt 接入

启动 mqtt server：
``
docker run -d --name emqx -p 1883:1883 -p 8083:8083 -p 8883:8883 -p 8084:8084 -p 18083:18083 emqx/emqx
``

访问地址：
http://127.0.0.1:8080/swagger-ui/index.html