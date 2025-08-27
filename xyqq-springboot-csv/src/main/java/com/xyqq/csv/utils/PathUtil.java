package com.xyqq.csv.utils;

public class PathUtil {

    public static String getSystemPath() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            // Windows 系统路径
            return "C:\\data\\csv-storage\\";
        } else {
            // Linux 系统路径
            return "/data/csv-storage/";
        }
    }

    public static void main(String[] args) {
        System.out.println("系统存储路径: " + getSystemPath());
    }
}