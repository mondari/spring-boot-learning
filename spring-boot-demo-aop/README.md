# spring-boot-demo-aop

主要内容：

- 环绕通知注解使用示例
- 静态代理示例
- JDK 动态代理示例
- CGlib 动态代理示例
- AOP 代理示例
- AOP Introduction 引入通知示例



待补充 AOP 原理相关内容

参考：[Spring AOP之使用注解创建切面](https://www.jianshu.com/p/6f40dddd71a5)

## AOP 的相关概念

- Aspect **切面**：不同的类会有相同的关注点，将其抽出来模块化，就是一个切面。
- Join point **连接点**：程序执行的一个点，比如方法的执行或异常的处理。在 Spring AOP 中，连接点表示一个方法的执行。
- Advice **通知**：在连接点上执行的操作。通知有不同类型，包括环绕通知、前置通知、后置通知（即 “around”, “before” and “after” )。很多 AOP 框架会在通知上引入一系列的拦截器。
- Pointcut **切入点**：匹配连接点的一个表达式。通知会在与切入点匹配的连接点上运行。
- Introduction **引入**：表示创建目标类的代理类，该代理类在目标类的基础上添加额外的方法或属性。这样子就能做到目标类不实现一个新接口，代理类也能使用该接口中的方法。
- Target object **目标对象**：被代理对象（“proxied object”），也叫被通知对象（“advised object”）。
- AOP proxy **代理对象**：AOP 框架在目标对象基础上创建的对象。
- Weaving **织入**：将切面功能应用到目标对象的过程。该过程可以在编译期（compile time）、加载期（load time）、运行期（runtime）完成。

参考：

https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-introduction-defn

## AOP 切入点表达式

- `execution`: 匹配方法

  具体的表达式形式如下：

  ```
  execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
  ```

  - modifiers-pattern：访问修饰符
  - **ret-type-pattern**：返回值类型
  - declaring-type-pattern：声明的类型
  - **name-pattern**：方法名称
  - **param-pattern**：入参，`()` 匹配无参方法，`(*)` 匹配一个任意类型的方法，`(..)` 匹配任意数量任意类型的方法
  - throws-pattern：异常抛出的形式

  以上唯独加粗的不能省略，因为这是构成方法签名的必要条件。

  示例：

  - 匹配所有 public 方法

    ```
    execution(public * *(..))
    ```

  - 匹配所有 `set` 开头的方法

    ```
    execution(* set*(..))
    ```

  - 匹配 `AccountService` 类型下的所有方法

    ```
    execution(* com.xyz.service.AccountService.*(..))
    ```

  - 匹配 `service` 包下的所有方法（这种情况下建议用 `within` 更方便）

    ```
    execution(* com.xyz.service.*.*(..))
    ```

  - 匹配 `service` 包及其子包下的所有方法（这种情况下建议用 `within` 更方便）

    ```
    execution(* com.xyz.service..*.*(..))
    ```

  - 再来一个复杂一点的

    ```
    execution(!static com.common.vo.Result *(..)) && (@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController))
    ```

    表示匹配持有 `@Controller` 或 `@RestController` 注解的类型下返回类型为 `Result` 的非静态方法。

- `within`: 匹配指定类型内的方法

  示例：

  - 匹配 `service` 包下所有方法

    ```java
    within(com.xyz.service.*)
    ```

    相当于

    ```
    execution(* com.xyz.service.*.*(..))
    ```

    

  - 匹配 `service` 包及其子包下所有方法

    ```java
    within(com.xyz.service..*)
    ```

    相当于

    ```
    execution(* com.xyz.service..*.*(..))
    ```

    

- **`this`: 匹配指定类型下的所有方法（指定类型或其实现类需要注册为 Bean）**

  - 匹配 `UserDetailsService` 接口的实现类（需要注册为 Bean）下面的所有方法

    ```
    this(org.springframework.security.core.userdetails.UserDetailsService)
    ```

    如果把 `this` 换成 `target` 或 `within` ，是找不到切点的

  - **如果将上面的 `UserDetailsService` 换成其子接口 `UserDetailsManager`，或其实现类 `InMemoryUserDetailsManager`，其切点并不是其子接口或其实现类的所有方法**

    ```
    this(org.springframework.security.provisioning.UserDetailsManager)
    ```

    或

    ```
    this(org.springframework.security.provisioning.InMemoryUserDetailsManager)
    ```

  

- **`target`: 匹配指定类的实例内的方法**

  - 匹配 `SysUserServiceImpl` 下所有方法

    ```java
    target(com.ruoyi.project.system.service.impl.SysUserServiceImpl)
    ```

    **如果将 `SysUserServiceImpl` 换成其实现的接口 `ISysUserService` ，则找不到切点。**

  - 用 `target` 来匹配 SysDictDataController 下的所有方法时，会将 SysDictDataController 继承的 BaseController 类的方法也包含进去

    ```java
    target(com.ruoyi.project.system.controller.SysDictDataController)
    ```

    将 `target` 换成 `within` 更合适

- `args`: 匹配入参为指定类型的方法

  比如下面这个匹配所有入参为 `Serializable` 接口实现类（包括 String 类）的方法

  ```java
  args(java.io.Serializable)
  ```

  

- `@target`: 匹配持有指定注解的类（不是接口）的实例（包括 Bean）内的方法。注意和 `@within` 的区别，`@target` 表示的是类的实例内的方法，`@within` 表示的是类和接口内的方法

  比如下面这个表示以持有 `@Transactional` 注解的类下面的方法为切点

  ```java
  @target(org.springframework.transaction.annotation.Transactional)
  ```

  

- `@args`: 匹配方法入参的类型持有指定注解的方法。

  比如下面这个表示以入参类型持有 `@Data` 注解的方法为切点

  ```java
  @args(lombok.Data)
  ```

  

- `@within`: 匹配持有指定注解的类型内的方法。

  比如下面这个表示以持有 `@RestController` 注解的类或接口下面的方法为切点

  ```java
  @within(org.springframework.web.bind.annotation.RestController)
  ```

  

- `@annotation`: 匹配持有指定注解的方法。

  比如下面这个表示以持有 `@Log` 注解的方法为切点

  ```java
  @annotation(com.xxx.framework.aspectj.lang.annotation.Log)
  ```

  

参考：

https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#aop-pointcuts-designators

[Spring 之AOP AspectJ切入点语法详解（最全了，不需要再去其他地找了）](https://blog.csdn.net/jinnianshilongnian/article/details/84156354)