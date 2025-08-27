这里有一个完整的 Spring Boot 实现示例，将数组数据写入 CSV 文件到指定目录。

### **实现步骤：**
1. **使用 OpenCSV 库**（也可以使用 Java 自带的 FileWriter）。
2. **定义 CSV 工具类**，负责写入数据。
3. **提供一个 Spring Boot 服务接口**，调用写入 CSV 的功能。

---

### **完整代码实现**

#### **1. 添加 OpenCSV 依赖（如果需要）**
如果你的项目使用的是 Maven，可以在 `pom.xml` 中添加 OpenCSV 依赖：
```xml
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.7.1</version>
</dependency>
```

---

#### **2. CSV 工具类**
```java
package com.xyqq.csv.utils;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvUtil {
    /**
     * 将数据写入 CSV 文件
     * @param data      要写入的数据（List<List<String>> 格式）
     * @param filePath  目标文件路径
     */
    public static void writeCsv(List<List<String>> data, String filePath) {
        File file = new File(filePath);
        // 确保目录存在
        file.getParentFile().mkdirs();
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            for (List<String> rowData : data) {
                writer.writeNext(rowData.toArray(new String[0]));
            }
        } catch (IOException e) {
            throw new RuntimeException("写入 CSV 失败：" + e.getMessage(), e);
        }
    }
}
```

---

#### **3. 在 Spring Boot 服务中调用**
创建一个 Controller，通过 API 触发 CSV 文件的生成。

```java
package com.xyqq.csv.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {

    @GetMapping("/generate")
    public String generateCsv() {
        // 准备数据
        List<List<String>> data = Arrays.asList(
                Arrays.asList("ID", "Name", "Age"),
                Arrays.asList("1", "Alice", "23"),
                Arrays.asList("2", "Bob", "30"),
                Arrays.asList("3", "Charlie", "27")
        );

        // 指定 CSV 文件路径
        String filePath = "/tmp/output.csv"; // Linux/macOS 目录
        // String filePath = "C:/temp/output.csv"; // Windows 目录

        // 写入 CSV
        CsvUtil.writeCsv(data, filePath);

        return "CSV 文件已生成：" + filePath;
    }
}
```

---

### **4. 运行和测试**
1. **启动 Spring Boot 项目**
2. **访问接口**  
   浏览器或 Postman 访问：
   ```
   http://localhost:8080/csv/generate
   ```
3. **检查 `/tmp/output.csv`（Linux/macOS）或 `C:/temp/output.csv`（Windows）**

---

### **生成的 CSV 内容**
```
ID,Name,Age
1,Alice,23
2,Bob,30
3,Charlie,27
```

---

### **优化**
- **支持动态路径**：可以在 `application.yml` 里配置路径，让用户自定义。
- **支持 Excel**：可以使用 Apache POI 生成 `.xlsx`。
- **异常处理**：增强日志和错误处理逻辑。