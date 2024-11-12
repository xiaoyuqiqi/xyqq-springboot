在使用 Lombok 时，可以通过 `@RequiredArgsConstructor` 来自动生成构造函数并实现 Spring 组件的依赖注入。下面是一个 Lombok 注入 Spring 组件的简单示例：

### 1. 引入 Lombok 依赖
首先确保在 `pom.xml` 中引入 Lombok 依赖：

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.28</version> <!-- 请根据实际情况调整版本 -->
    <scope>provided</scope>
</dependency>
```

### 2. 使用 `@RequiredArgsConstructor` 进行构造注入

通过 `@RequiredArgsConstructor` 注解，Lombok 会自动为所有 `final` 字段生成构造函数。因此，只需将依赖的 Spring 组件声明为 `final`，就可以通过构造函数注入的方式完成依赖注入。

#### 示例代码

```java
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  // Lombok 自动生成包含 final 字段的构造函数
public class UserService {

    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public void registerUser(User user) {
        userRepository.save(user);
        notificationService.sendWelcomeNotification(user);
    }
}
```

在这个例子中：
- `UserService` 通过构造函数注入了 `UserRepository` 和 `NotificationService`。
- `@RequiredArgsConstructor` 自动生成了一个包含 `userRepository` 和 `notificationService` 的构造函数，并将它们注入到 `UserService` 中。

### 3. 相关组件的定义示例

假设有两个相关的 Spring 组件 `UserRepository` 和 `NotificationService`：

```java
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public void save(User user) {
        // 保存用户逻辑
    }
}

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendWelcomeNotification(User user) {
        // 发送欢迎通知逻辑
    }
}
```

### 4. 使用示例

在控制器或其他服务中使用 `UserService`：

```java
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
    }
}
```

### 总结
- `@RequiredArgsConstructor` 非常适合构造函数注入方式，简化了代码的编写。
- 需要注入的组件使用 `final` 声明即可。

这样，Lombok 将自动生成构造函数，Spring 自动注入依赖，代码更加简洁。