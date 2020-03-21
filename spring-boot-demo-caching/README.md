# spring-boot-demo-caching

本demo参考https://spring.io/guides/gs/caching/，并在其基础上增加redis缓存。
本emo还存在问题，缓存的对象反序列化时结果为LinkedHashMap，而不是为对应的实体类。