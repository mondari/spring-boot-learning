# spring-boot-demo-springdoc

- Swagger UI 页面地址： `http://localhost:8080/swagger-ui.html` 
- API 地址： `http://localhost:8080/v3/api-docs`

参考文档：https://springdoc.org/

### [Differentiation to Springfox project](https://springdoc.org/#differentiation-to-springfox-project)

- OAS 3 was released in July 2017, and there was no release of `springfox` to support OAS 3. `springfox` covers for the moment only swagger 2 integration with Spring Boot. The latest release date is June 2018. So, in terms of maintenance there is a big lack of support lately.
- We decided to move forward and share the library that we already used on our internal projects, with the community.
- The biggest difference with `springfox`, is that we integrate new features not covered by `springfox`:
- The integration between Spring Boot and OpenAPI 3 standard.
- We rely on on `swagger-annotations` and `swagger-ui` only official libraries.
- We support new features on Spring 5, like `spring-webflux` with annotated and functionnal style.
- We do our best to answer all the questions and address all issues or enhancement requests

## [Migrating from SpringFox](https://springdoc.org/#migrating-from-springfox)

如何从 SpringFox 迁移

