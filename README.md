# spring-boot-2-study
SpringBoot2.x 练手项目

### 生成项目目录结构：
1.安装 tree 命令(mac)
`brew install tree`

2.生成项目目录树
`tree -d -I "target|test|resources|src"`


### 项目说明
```
├── apache-velocity （apache velocity 模版引擎，可生成 Java 类定义文件以及其他语言的定义文件。用于生成项目框架比较方便）
├── grpc
│   ├── devh-grpc-api
│   ├── devh-grpc-client
│   ├── devh-grpc-server
│   ├── devh-grpcs-client
│   └── devh-grpcs-server
├── mqtt (物联网协议接入)
├── swagger3 (swagger3 自动生成文档的实现)
└── websocket
    ├── java-native-annotation (java 原生注解方式实现)
    ├── spring-native (spring 低级别封装实现)
    └── stomp (stomp 高级协议的实现)

```