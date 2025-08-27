package com.xyqq.csv.controller;

import com.xyqq.csv.model.User;
import com.xyqq.csv.utils.CsvUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/csv")
public class CsvController {


//    private static final String UPLOAD_DIR = "/tmp/"; // Linux/macOS 目录
    // private static final String UPLOAD_DIR = "C:/temp/"; // Windows 目录
    private static final String UPLOAD_DIR = "C:/Users/10298/Desktop/csv/";

    @PostMapping("/upload")
    public List<User> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传的文件为空");
        }

        // 保存文件到本地
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        File dest = new File(filePath);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("文件保存失败：" + e.getMessage(), e);
        }

        // 解析 CSV
        return CsvUtil.readCsv(filePath);
    }

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

        // 导出
//        csvController.generateCsv();

        String filePath = "C:/Users/10298/Desktop/csv/output.csv";
        // 读取
        List<User> users = CsvUtil.readCsv(filePath);
        for (User user : users) {
            System.out.println(user);
        }

    }
}
