package io.renren.common.utils;


import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Random;
import java.util.UUID;


@Slf4j
public class RandomUtil {

    private static final Random random = new Random();

    public static String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
     * 获取一个随机4位数的短信验证码
     */
    public static String getRandomSmsCode() {
        Random random = new Random();
        return random.nextInt(9000 - 1) + 1000 + "";
    }


}

