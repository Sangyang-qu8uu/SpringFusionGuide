Mockito的使用

1.简介

Mockito 是一个用于 Java 应用程序的流行测试框架，专门用于 **模拟（mocking）对象**。它帮助开发者在进行单元测试时，模拟对象的行为，避免对真实的依赖进行调用，比如数据库、外部服务或复杂逻辑，从而使测试更加独立和高效。

2.使用

由于我这个工程项目采用的是springboot版本2.6.13，且引入了`spring-boot-starter-test`包相关坐标依赖，如果没有Mockito相关依赖，则需要单独引入,并且下面的案例兼容了 JUnit 4 或 JUnit 5 中使用，因此我还引入了

````xml
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>4.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <version>4.0.0</version>
        </dependency>
        <!--spring-boot-starter-test无junit4，所以需要单独使用-->
        <dependency>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
            <scope>test</scope>
        </dependency>
````

3.Mockito 组成

在学习 **Mockito** 时，有几个重要的概念需要理解，这些概念能够帮助你掌握如何在测试中有效地使用 Mockito 进行单元测试。以下是 Mockito 中的关键概念：`Mock/Spy对象`，`Stub（方法插桩）`,`验证（Verification）`,`参数匹配器（Argument Matcher）`,`捕获器（Argument Captor）`，`行为驱动开发（BDD）`，`异常处理`,`调用真实方法（doCallRealMethod）`,`Mock 注解（@Mock 和 @InjectMocks）`

4.创建mock/spy对象方式

4.1mock对象与spy对象

|          | 方法插桩     | 方法不插桩           | 作用对象 | 最佳实践         |
| -------- | ------------ | -------------------- | -------- | ---------------- |
| mock对象 | 执行插桩逻辑 | 返回mock对象的默认值 | 类，接口 | 被测试类或其依赖 |
| spy对象  | 执行插桩逻辑 | 调用真实方法         | 类，接口 | 被测试类         |

4.2初始化mock/spy对象的方式

<table>
  <tr>
    <th></th>
    <th>方法一</th>
    <th>方法二</th>
    <th>方法三</th>
  </tr>
  <tr>
    <td>junit4</td>
    <td>@RunWith(MockitoJUnitRunner.dass)+@Mock等注解</td>  
    <td rowspan="2">Mockito.mock(X.class)等静态方法</td>
    <td rowspan="2">MockitoAnnotations.
openMocks(this)+@Mock等注解</td>
  </tr>
  <tr>
    <td>junit4</td>
    <td>@RunWith(MockitoJUnitRunner.dass)+@Mock等注解</td>
  </tr>
</table>

5.方法插桩（Stubbing）

Stubbing 是指为模拟对象的方法指定特定的返回值或行为。通过这种方式，可以在测试中定义当某些方法被调用时应该返回的值或抛出的异常。

- 返回指定值

- void返回值方法插桩

- 插桩的两种方式

  when(obj.someMethod(0).thenXxx()):用于mock对象

  doXxx(0.when(obj).someMethod():其中obj可以是mock/spy对象

- 抛异常

- 多次插桩

- thenAnswer

- 执行真正的原始方法

- verify的使用

  

示例：

```sh
Mockito.when(mockedList.get(0)).thenReturn("first element");
```









### 总结：
- **Mock 和 Spy**：Mock 是完全虚拟的对象，Spy 依赖真实对象。
- **Stubbing（插桩）**：控制方法行为，返回特定值或抛出异常。
- **Verification（验证）**：确认方法调用及其参数。
- **Argument Matchers（参数匹配器）**：灵活匹配方法参数。
- **Argument Captor（参数捕获器）**：捕获并检查方法调用时的参数。
- **BDD 风格**：`given()` 和 `willReturn()` 提供行为驱动开发风格的语法。
- **异常处理和真实方法调用**：测试异常抛出和调用真实方法。
- **注解**：使用 `@Mock` 和 `@InjectMocks` 自动初始化和依赖注入。 

这些概念是 Mockito 的基础，掌握它们可以帮助你在测试中更好地控制和验证代码的行为。







