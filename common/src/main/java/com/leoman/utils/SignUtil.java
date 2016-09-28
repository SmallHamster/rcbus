package com.leoman.utils;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/9/28.
 */
public class SignUtil {
    private static String token = "weixin";

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        boolean result = false;

        // 对token、timestamp和nonce按字典序排序
        String[] array = new String[]{token, timestamp, nonce};
        Arrays.sort(array);

        // 将三个参数字符拼接成一个字符串
        String str = array[0].concat(array[1]).concat(array[2]);

        String sha1Str = null;
        try {
            // 对拼接后的字符串进行sha1加密
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(str.getBytes());
            sha1Str = byte2str(digest);
        }
        catch(Exception e) {
        }

        if(sha1Str != null &&  sha1Str.equals(signature)) {
            result = true;
        }

        return result;
    }

    /*
     * 将字节数组转换成字符串
     */
    public static String byte2str(byte[] array) {
        StringBuffer hexstr = new StringBuffer();
        String shaHex="";
        for(int i = 0; i < array.length; i++) {
            shaHex = Integer.toHexString(array[i] & 0xFF);
            if(shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
    }
}
