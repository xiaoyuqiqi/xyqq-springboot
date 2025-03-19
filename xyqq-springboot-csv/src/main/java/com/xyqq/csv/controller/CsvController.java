package com.xyqq.csv.controller;

import com.xyqq.csv.utils.CsvUtil;
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
//        String filePath = "/tmp/output.csv"; // Linux/macOS 目录
//         String filePath = "C:/temp/output.csv"; // Windows 目录
        String filePath = "C:/Users/10298/Desktop/csv/output.csv"; // Windows 目录


        // 写入 CSV
        CsvUtil.writeCsv(data, filePath);

        return "CSV 文件已生成：" + filePath;
    }

    public static void main(String[] args) {
        CsvController csvController = new CsvController();
        csvController.generateCsv();
    }
}
