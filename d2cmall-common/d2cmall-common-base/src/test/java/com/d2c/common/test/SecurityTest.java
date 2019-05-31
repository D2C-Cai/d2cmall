package com.d2c.common.test;

import com.d2c.common.base.utils.ByteUt;
import com.d2c.common.base.utils.security.Base64Ut;
import com.d2c.common.base.utils.security.RSAUt;
import com.d2c.common.base.utils.security.core.RSAKey;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具测试
 *
 * @author user
 */
public class SecurityTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //	@Test
    public void test() throws Exception {
        logger.info("开始测试....");
        long max = 0;
        long sum = 0;
        for (int i = 0; i < 100; i++) {
            long start = System.currentTimeMillis();
            RSAKey key = RSAUt.initKey();
            logger.info("privateKey:" + key.getPrivateStr());
            logger.info("publicKey:" + key.getPublicStr());
            long end = System.currentTimeMillis();
            long time = end - start;
            sum = sum + time;
            if (max < time) {
                max = time;
            }
            logger.info("RSAUt.initKey :" + time + "ms 最大:" + max);
        }
        logger.info("总共用时:" + sum + "ms  平均:" + sum / 100 + "ms 最大:" + max + "ms");
//		RSAKey key = RSAUt.initKey();
//		
//		logger.info("privateKey:" + key.getPrivateStr());
//		logger.info("publicKey:" + key.getPublicStr());
//		String str = "abcdefg12312";
//		logger.info("原报文...." + str);
//		String sec = RSAUt.encodePrivate(key.getPrivateStr(), str);
//		logger.info("RSA Private加密密文..." + sec);
//		logger.info("RSA Public解密密文..." + RSAUt.decodePublic(key.getPublicStr(), sec));
    }

    @Test
    public void testByte() throws Exception {
        logger.info("开始测试testByte....");
        String str = "吴乐璐测试...";
        String ec = Base64Ut.encodeUTF(str);
        logger.info("str...." + str);
        logger.info("encode...." + ec);
        logger.info("decode...." + Base64Ut.decodeUTF(ec));
        String str1 = ByteUt.toStrISO(ByteUt.toByte(str));
        String ec1 = Base64Ut.encode(str1);
        logger.info("str1...." + str1);
        logger.info("encode...." + ec1);
        logger.info("decode...." + Base64Ut.decode(ec1));
        logger.info("decode...." + ByteUt.toStr(Base64Ut.decodeByte(ByteUt.toByteISO(ec1))));
//		logger.info("MD5Ut.md5...." + MD5Ut.md5(str));
//		logger.info("SHAUt.sha256...." + ByteUt.toHexStr(SHAUt.sha256(ByteUt.toByte(str))));
    }

}
