# spring-boot-rabbitmq

[TOC]

## RabbitMQ 应用场景



## RabbitMQ 四种交换机类型

| Name             | Default pre-declared names                      | Use                    | routing key |
| ---------------- | ----------------------------------------------- | ---------------------- | ----------- |
| Direct exchange  | (Empty string) and **amq.direct**               | unicast 单播，一对一   | Yes         |
| Fanout exchange  | **amq.fanout**                                  | broadcast 广播，一对多 | No          |
| Topic exchange   | **amq.topic**                                   | multicast 发布订阅模式 | Yes         |
| Headers exchange | **amq.match** (and **amq.headers** in RabbitMQ) | headers attribute      | No          |