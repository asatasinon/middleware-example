package com.raven.middleware.example.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author raven
 * @date 2024/6/25 09:58
 * @description
 */
public class CipherUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128; // AES key size in bits

    private static final String KEY = "This_Is_A_24_Byte_Key_24"; // 请确保密钥长度为16、24或32字节
    private static final String IV = "lPflW8SfzKblg7pjc+eHcQ=="; // 请确保IV长度为16字节

    private CipherUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * AES 加密
     *
     * @param content 明文内容
     * @param key     密钥，长度必须为16、24、或32字节
     * @param iv      初始化向量，长度必须为16字节
     * @return Base64编码的密文字符串
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);

        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES 解密
     *
     * @param encryptedContent Base64编码的密文字符串
     * @param key              密钥，长度必须为16、24、或32字节
     * @param iv               初始化向量，长度必须为16字节
     * @return 明文内容
     */
    public static String decrypt(String encryptedContent, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decodedBytes = Base64.getDecoder().decode(encryptedContent);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 生成随机的16字节初始化向量
     *
     * @return 随机生成的IV字符串
     */
    public static String generateRandomIV() {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16];
        random.nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 生成AES密钥
     *
     * @param key 密钥字符串
     * @return AES密钥
     * @throws Exception 异常
     */
    private static SecretKeySpec generateKey(String key) throws Exception {
        byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest(key.getBytes());
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    /**
     * AES-ECB模式加密
     *
     * @param key  密钥
     * @param data 明文数据
     * @return Base64编码的密文
     * @throws Exception 异常
     */
    public static String encrypt(String key, String data) throws Exception {
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES-ECB模式解密
     *
     * @param key           密钥
     * @param encryptedData Base64编码的密文
     * @return 解密后的明文
     * @throws Exception 异常
     */
    public static String decrypt(String key, String encryptedData) throws Exception {
        SecretKeySpec secretKeySpec = generateKey(key);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        return new String(cipher.doFinal(decodedData));
    }

    public static String getLog(int i) {
        return "GAPS000230209001394792962,[INFO ],2024-03-04 08:47:16.609,JRESHandler-25.1.32.117:22222-thread-223,fulllink: {\"log_id\":\"d67fcc8c47f94d48b484f6a500d42919\",\"span_id\":\"0\",\"depot_id\":\"a0\",\"module_type\":\"CS\",\"timestamp\":\"2024-03-04 08:47:16.609\",\"name\":\"ServiceUtils\",\"server_name\":\"NPAC_25.1.32.117_5DB4EC0D\",\"response_status\":\"0\",\"version\":\"\",\"span_type\":\"\",\"app_type\":\"\",\"mypackage\":{\"error_info\":null,\"error_no\":null,\"business_datas\":{\"header\":{\"send_sysname\":\"NPAC\",\"recv_sysname\":\"NPA\",\"sender\":\"NPAC\",\"receiver\":\"NPA\",\"channel\":\"1\",\"msg_type\":\"npa.009.001\",\"msg_id\":\"NPAC000020209001394969605\",\"send_time\":\"2024-03-04T08:47:16.609\",\"checksum\":\"\",\"signature\":\"\",\"exts\":{\"request_ip\":\"25.1.208.1\",\"log_id\":\"d67fcc8c47f94d48b484f6a500d42919\",\"access_type\":\"C000\",\"span_id\":\"0.9\",\"sender_lang\":\"zh-CN\",\"token\":\"d235c29cb8a52586844fd5bf52478ef33a563692ff3b5118124a5bc0ec018e5e\",\"audit_userid\":\"0000009166\",\"clientuser\":{\"accts\":[{\"acct_no\":\"01100004086\",\"acct_name\":\"某银行股份有限公司\"}],\"user_name\":\"杨某\",\"contact_tel\":\"0755-82477033\",\"line_id\":\"ZY00000879\",\"user_afflt_dept\":\"\",\"legal_inst_flag\":\"620\",\"contact_email\":\"\",\"user_id\":\"0000009166\",\"user_logon_name\":\"NPLYY2719\",\"contact_mobile\":\"13794476501\",\"cust_name\":\"某银行股份有限公司\",\"cust_id\":\"0000000014\",\"cust_short_name\":\"某银行\"},\"func_code\":\"NA220823CB\",\"selectedacct\":{\"acct_no\":\"01100004086\",\"acct_name\":\"某银行股份有限公司\"},\"serviceid\":\"npac.009.001-addUnichk\",\"request_port\":50496,\"seq\":\"CT-2024-03-04-08:47:17-24475426\"}},\"bodys\":[{\"version\":\"1.00\",\"trns_type\":\"npa.009.001\",\"trns_id\":\"NPAC000020209001394969605\",\"exts\":{\"longtext\":{\"sellerInvest\":\"敏感信息替换\",\"payMarginFlag\":\"0\",\"ostdIntrstTotalAmnt\":14250082.51,\"ostdPniFeeTotalAmnt\":191963275.68,\"borrowerAvgAge\":\"\",\"sellerName\":\"某银行股份有限公司\",\"initPrice\":\"75000000.00\",\"npaAcctNum\":\"7\",\"npaBidMethCode\":\"2\",\"riskControlMeasures\":\"敏感信息替换\",\"borrowerAvgCreditAmnt\":\"\",\"overdueInitCnfrmStd\":\"贷款到期后未足额偿还本金和利息即认为开始逾欠。\",\"unichkId\":\"\",\"otherFeeAmnt\":\"716194.00\",\"agrmtTransfFlag\":\"0\",\"npaAsstNum\":7,\"npaProjTempNo\":\"LSNPL2024001220\",\"intentBuyerQuat\":\"敏感信息替换\",\"npaProjStatusCode\":\"02\",\"borrowerAvgOstdPniBal\":27321011.67,\"bizFlagCode\":\"\",\"transcBaseDate\":\"20231105\",\"projName\":\"某银行股份有限公司关于2024年某投资控股集团有限公司等7户不良贷款转让项目\",\"projAttachmentInfoReqList\":[{\"attachment_id\":\"ATT20240303129860\",\"npa_biz_entity_id\":\"LSNPL2024001220\",\"npa_biz_entity_type_code\":\"01\",\"file_strorage_id\":\"01_b91df4baf6824196a80626c67a45883c\",\"file_name\":\"债权资产转让协议（对公批量）.docx\",\"npa_file_type_code\":\"103\",\"del_flag\":\"\",\"complete_date\":\"20240303\",\"create_datetime\":\"2024-03-03 16:16:36\"}],\"nplProjTypeCode\":\"2\",\"sellerAcct\":\"01100004086\",\"ostdPrcplTotalAmnt\":176996999.17,\"npaProjId\":\"\",\"announceDate\":\"20240308\",\"regSrcCode\":\"1\",\"ostdPniTotalAmnt\":191247081.68,\"otherRemark\":\"\",\"projContactInfoList\":[{\"contactEmail\":\"dengjiangshan098@pingan.com.cn\",\"contactName\":\"邓某某\",\"contactMob\":\"13770506060\",\"contactAddr\":\"敏感地理信息替换\",\"npaProjTempNo\":\"LSNPL2024001220\",\"contactDept\":\"某某分行特殊资产管理部\"},{\"contactEmail\":\"sunyinan932@pingan.com.cn\",\"contactName\":\"孙某某\",\"contactMob\":\"17351777327\",\"contactAddr\":\"敏感地理信息替换\",\"npaProjTempNo\":\"LSNPL2024001220\",\"contactDept\":\"某某分行特殊资产管理部\"}],\"sglBorrowerMaxOstdPniBal\":\"73898820.53\",\"projRemark\":\"\",\"transfMethCode\":\"2\",\"currencyCode\":\"01\",\"assetRecordId\":\"ARI20240303004830\"},\"npaBizEntityId\":\"LSNPL2024001220\",\"prmlnrChkUserId\":\"\",\"investmentInfoId\":\"\",\"npaUnichkOpTypeCode\":\"01\",\"ltextIdN\":\"\",\"checkUserId\":\"\",\"npaBizSceneCode\":\"01\",\"inputUserName\":\"杨某\",\"checkOpinion\":\"\",\"npaBizAppInstName\":\"某银行股份有限公司\",\"retrunFlag\":\"\",\"listId\":\"\",\"unichkId\":\"UHK20240304008210\",\"asstAcctId\":\"01100004086\",\"checkUserName\":\"\",\"method\":\"addUnichk\",\"projName\":\"某银行股份有限公司关于2024年某投资控股集团有限公司等7户不良贷款转让项目\",\"inputUserId\":\"0000009166\",\"argType\":\"com.yindeng.cfs.npa.model.unichk.UnichkReq\",\"npaProjId\":\"\",\"sysChnlCode\":\"1\",\"firstCheckOpinion\":\"\",\"prmlnrChkUserName\":\"\",\"npaBizTypeCode\":\"01\"}}]}}}\n";
    }

    public static void main(String[] args) throws Exception {

        //        String key = "ThisIsA16ByteKey"; // 请确保密钥长度为16、24或32字节
        //        String iv = generateRandomIV(); // 生成或获取一个16字节的IV
        //        System.out.println("IV: " + iv);

        String key = KEY; // 请确保密钥长度为16、24或32字节
        String iv = IV; // 生成或获取一个16字节的IV
        System.out.println("IV: " + iv);
        //
        //        Map<String, String> logMap = new HashMap<>();
        //
        //        for (int i = 0; i < 10; i++) {
        //            logMap.put("log" + i, getLog(i));
        //        }
        //        logMap.put("1", "Hello, this is a secret message.");
        //
        //        List<String> logList = new ArrayList<>(1000000);
        //        for (int i = 0; i < 1000000; i++) {
        //            logList.add(logMap.get("log" + (i % 10)));
        //        }
        //
        //        System.out.println("logList size: " + logList.size());
        //
        //        long start = System.currentTimeMillis();
        //        for (int i = 0; i < logList.size(); i++) {
        //            String log = logMap.get("log" + i);
        //        }
        //        long end = System.currentTimeMillis();
        //
        //        System.out.println(String.format("not Encrypt start: %s, end: %s, Time: %s", start, end, end - start));
        //
        //        long encryptedStart = System.currentTimeMillis();
        //        for (int i = 0; i < logList.size(); i++) {
        //            String log = logList.get(i);
        //            String encrypted = encrypt(log, key, iv);
        //        }
        //        long encryptedEnd = System.currentTimeMillis();
        //
        //        System.out.println(String.format("Encrypt start: %s, end: %s, Time: %s", encryptedStart, encryptedEnd,
        //            encryptedEnd - encryptedStart));

        //        String encrypted = encrypt(key, logMap.get("log1"));
        //        System.out.println("Encrypted: " + encrypted);
        //
        //        String decrypted = decrypt(key, encrypted);
        //        System.out.println("Decrypted: " + decrypted);

        String content = "Hello, this is a secret message.";
        String encrypted = encrypt(content, key, iv);
        System.out.println("Encrypted: " + encrypted);

        String decrypted = decrypt(encrypted, key, iv);
        System.out.println("Decrypted: " + decrypted);

    }
}
