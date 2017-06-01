package com.commons.image;

import lombok.Data;

import java.util.Random;

/**
 * 生成图片验证码
 */
@Data
public final class ImageCaptcha {
    //指定图片的宽度
    private static int width = 200;
    //指定图片的高度
    private static int height = 40;
    //指定所有的字符(不含数字0、1;小写字母o、l;大写字母O、I)
    public static String CHAR = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    /**
     * 随机指定长度的字符串
     *
     * @param len
     * @return
     */
    private static String randomStr(int len) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(CHAR.charAt(random.nextInt(CHAR.length())));
        }
        return sb.toString();

    }

    /**
     * 生产一张png格式的验证图片在指定的位置
     *
     * @param strlen 验证码长度
     * @param file   文件位置
     * @return 是否成功
     */
    public static String pngCaptcha(int strlen, String file) {
        String random = randomStr(strlen);
        if (CaptchaUtil.pngCaptcha(random, width, height, file)) {
            return random;
        }
        return "";
    }

    public static String gifCaptch(int strlen, String file) {
        String random = randomStr(strlen);
        if (CaptchaUtil.gifCaptcha(random, width, height, file)) {
            return random;
        }
        return "";
    }
}
