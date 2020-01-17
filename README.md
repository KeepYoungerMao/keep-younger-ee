## JAVA WEB 原生系统测试

***

#### 环境
> JDK：1.8
> TOMCAT：8.5.37

#### 说明
* 执行过程
    * 由一个Servlet处理多个请求。
    * Servlet初始化时加载请求存入缓存。
    * 执行请求时，遍历缓存找到正确请求路径
* 使用
    * 模拟Spring。在请求处理类上添加 `@Web` 注解，可在初始化时扫描到。
    * 请求类上添加 `@ServletMapping` 注解，可作为此类统一前置路径。
    * `@ServletMapping` 注解可配置多个路径，可配置与类和方法上，
    * 但如果配置在类上，只取首个路径。
    * 方法上可以添加 `@ServletMapping` 、`@GetMapping` 、`@PostMapping` 、
    * `@PutMapping` 、`@DeleteMapping` 注解。可配置多个路径。
* 不足
    * 目前未实现初始化时重复路径判定。正常情况不能出现重复路径。
    * 目前注解只支持配置路径参数。
    * 目前参数传递至支持传递`HttpServletRequest`和`HttpServletResponse`参数，未对其他参数适配。
#### 配置（web.xml）
    # 配置初始化参数（初始化执行类和初始化包扫描路径）
    # 参数名称不可变。初始化类可自己编写实现；包扫描路径可更改
    
    <context-param>
        <param-name>MethodHandlerMapping</param-name>
        <param-value>com.mao.servlet.handler.HttpMethodHandlerMapping</param-value>
    </context-param>
    <context-param>
        <param-name>PacketScan</param-name>
        <param-value>com.mao</param-value>
    </context-param>
    
    # 配置Servlet（Servlet可自己编写实现）
    # pattern: ".mao"已在代码中写死。
    
    <servlet>
        <servlet-name>TestServlet</servlet-name>
        <servlet-class>com.mao.servlet.GlobalServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>TestServlet</servlet-name>
        <url-pattern>*.mao</url-pattern>
    </servlet-mapping>
#### 模拟测试
    参考 TestWeb 类