package com.xyqq.csv.utils;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
}

