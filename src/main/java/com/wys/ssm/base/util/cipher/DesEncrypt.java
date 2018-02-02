package com.wys.ssm.base.util.cipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DesEncrypt {

    /** 加密、解密key. */
    private static final String PASSWORD_CRYPT_KEY = "pa1sdfsds";
    /** 加密算法,可用 DES,DESede,Blowfish. */
    private final static String ALGORITHM = "DES";

    public static void main(String[] args) throws Exception {
        String md5Password = "Hellow Word!";
        String key = PASSWORD_CRYPT_KEY;
        String str = DesEncrypt.encrypt(md5Password, key);
        System.out.println("str: " + str);
        str = DesEncrypt.decrypt(str, key);
        System.out.println("str: " + str);
        // 170377F3295BE501C7A3CA298CE508CE
        // 170377F3295BE501C7A3CA298CE508CE

        // String test = "Hellow Word!";
        // //DESPlus des = new DESPlus();//默认密钥
        // DESPlus des = new DESPlus("kEHrDooxWH");//自定义密钥
        // System.out.println("加密前的字符："+test);
        // System.out.println("加密后的字符："+des.encrypt(test));
        // System.out.println("解密后的字符："+des.decrypt(des.encrypt(test)));
    }

    /**
     * 对数据进行DES加密.
     * 
     * @param data 待进行DES加密的数据
     * @return 返回经过DES加密后的数据
     * @throws Exception
     */
    public final static String decrypt(String data) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()),
                PASSWORD_CRYPT_KEY.getBytes()));
    }

    /**
     * 对数据进行DES加密.
     * 
     * @param data 待进行DES加密的数据
     * @return 返回经过DES加密后的数据
     * @throws Exception
     */
    public final static String decrypt(String data, String key) throws Exception {
        return new String(decrypt(hex2byte(data.getBytes()), getKey(key
                .getBytes())));
    }

    /**
     * 对用DES加密过的数据进行解密.
     * 
     * @param data DES加密数据
     * @return 返回解密后的数据
     * @throws Exception
     */
    public final static String encrypt(String data) throws Exception {
        return byte2hex(encrypt(data.getBytes(), PASSWORD_CRYPT_KEY
                .getBytes()));
    }

    /**
     * 对用DES加密过的数据进行解密.
     * 
     * @param data DES加密数据
     * @return 返回解密后的数据
     * @throws Exception
     */
    public final static String encrypt(String data, String key) throws Exception {
        return byte2hex(encrypt(data.getBytes(), getKey(key
                .getBytes())));
    }

    /**
     * 用指定的key对数据进行DES加密.
     * 
     * @param data 待加密的数据
     * @param key DES加密的key
     * @return 返回DES加密后的数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(data);
    }

    /**
     * 用指定的key对数据进行DES解密.
     * 
     * @param data 待解密的数据
     * @param key DES解密的key
     * @return 返回DES解密后的数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(data);
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs.append("0").append(stmp);
            else
                hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     *
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private static byte[] getKey(byte[] arrBTmp) throws Exception {
        // 创建一个空的8位字节数组（默认值为0）
        byte[] arrB = new byte[8];

        // 将原始字节数组转换为8位
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        return arrB;
    }

}
