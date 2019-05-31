package com.d2c.common.test;

import com.d2c.common.base.utils.MathUt;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        logger.info("isNumber:" + MathUt.isNumber(11.0));
//		UserDTO bean = new UserDTO();
//
//		logger.info("UserDTO:" + BeanUt.isBean(bean) );
//		logger.info("Integer:" + BeanUt.isBean(1) );
//		logger.info("Map:" + BeanUt.isBean(new HashMap<>()) );
//		logger.info("List:" + BeanUt.isBean(new ArrayList<>()) );
//		for(Method md : UserDTO.class.getMethods()){
//			logger.info("getMethods:" + md.getName() );
//		}
//		
//		for(Method md : UserDTO.class.getDeclaredMethods()){
//			logger.info("getDeclaredMethods:" + md.getName() );
//		}
//
//		
//		for(Method md : ReflectUt.getAllMethods(UserDTO.class)){
//			logger.info("getAllMethods:" + md.getName() );
//		}
//		for(Field fd : UserDTO.class.getFields()){
//			logger.info("getFields:" + fd.getName() );
//		}
//		
//		for(Field fd : UserDTO.class.getDeclaredFields()){
//			logger.info("getDeclaredFields:" + fd.getName() );
//		}
//		
//		for(Field fd : ReflectUt.getAllFields(UserDTO.class)){
//			logger.info("getAllFields:" + fd.getName() );
//		}
    }

}
