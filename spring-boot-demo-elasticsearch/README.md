# spring-boot-demo-elasticsearch

Spring Boot 提供以下四种方式连接并操作 elasticsearch：

- 2.2.x版本，默认使用 RestHighLevelClient 或 RestClient 连接 elasticsearch
- 2.1.x版本，默认使用 Transport Client 连接 elasticsearch
- 使用 Jest Client 连接 elasticsearch
- 使用 ReactiveElasticsearchClient 连接 elasticsearch

Spring Data Elasticsearch 提供了 ElasticsearchRepository 接口非常方便地操作 es，Repository接口在不同 Spring Boot 版本其底层实现不同：

- 2.2.x版本，默认使用 ElasticsearchRestTemplate，如果没有就是用 ElasticsearchTemplate 
- 2.1.x版本，默认使用 ElasticsearchTemplate

ElasticsearchRestTemplate 和 ElasticsearchTemplate 都实现了 ElasticsearchOperations 接口，前者基于 RestHighLevelClient，后者基于 TransportClient。

