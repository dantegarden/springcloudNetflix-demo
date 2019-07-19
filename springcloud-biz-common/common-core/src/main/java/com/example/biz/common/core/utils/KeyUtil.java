package com.example.biz.common.core.utils;

import java.util.Random;


public class KeyUtil {

    /**
     * 生成唯一的主键
     * 此方法的唯一不是绝对的
     * 格式: 时间+随机数
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return System.currentTimeMillis() + String.valueOf(number);
    }
}
