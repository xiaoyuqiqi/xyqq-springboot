package com.xyqq.csv.utils;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.xyqq.csv.model.User;

import java.io.*;
import java.util.List;

public class CsvUtil {
    /**
     * 将数据写入 CSV 文件
     *
     * @param data     要写入的数据（List<List<String>> 格式）
     * @param filePath 目标文件路径
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


    /**
     * 解析 CSV 文件并转换为 User 对象列表
     * @param filePath CSV 文件路径
     * @return 用户列表
     */
    public static List<User> readCsv(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            return csvToBean.parse();
        } catch (IOException e) {
            throw new RuntimeException("读取 CSV 失败：" + e.getMessage(), e);
        }
    }
}

