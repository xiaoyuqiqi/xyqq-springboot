在 Spring Security 资源服务器中，可以通过解析 JWT 令牌来获取其中的用户信息。JWT 令牌通常包含 `claims`，比如 `sub`（用户 ID）、`name`（用户名）、`email`等字段，这些信息可以在 API 处理请求时获取。

下面是几种在 Spring Security 中提取 JWT 中用户信息的方式。

### 1. 使用 `@AuthenticationPrincipal` 注解直接获取用户信息

Spring Security 支持使用 `@AuthenticationPrincipal` 注解从当前认证信息中提取用户主体（principal）。在控制器方法中，你可以这样使用：

```java
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", jwt.getSubject()); // 获取用户 ID
        userInfo.put("email", jwt.getClaim("email")); // 获取 email
        userInfo.put("name", jwt.getClaim("name")); // 获取 name
        return userInfo;
    }
}
```

在这个例子中，`@AuthenticationPrincipal` 提取当前用户的 `Jwt` 对象，允许你从中读取 `claims`（例如 `email` 和 `name`）。当然，这些字段名取决于你的 JWT 中实际包含的内容。

### 2. 使用 `SecurityContextHolder` 获取 `Authentication` 对象

如果你需要在服务层或更深层调用中获取 JWT 信息，可以通过 `SecurityContextHolder` 获取 `Authentication` 对象。

```java
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.Authentication;

public class UserService {

    public Map<String, Object> getCurrentUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", jwt.getSubject());
        userInfo.put("email", jwt.getClaim("email"));
        userInfo.put("name", jwt.getClaim("name"));
        return userInfo;
    }
}
```

### 3. 自定义 `JwtAuthenticationConverter` 解析用户信息

如果需要在 Spring Security 中对 JWT 进行统一的用户信息提取和转换，可以自定义一个 `JwtAuthenticationConverter`，将 `Jwt` 的 `claims` 转换为自定义的用户信息。

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            .and()
            .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter()); // 使用自定义转换器
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("ROLE_");
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return converter;
    }
}
```

### 4. 检查 JWT 的 `claims` 内容
在测试阶段，你可以输出 `jwt.getClaims()` 以查看实际的字段内容，确保你的代码提取了正确的字段。

通过以上方法，你可以方便地从 JWT 令牌中提取用户信息。