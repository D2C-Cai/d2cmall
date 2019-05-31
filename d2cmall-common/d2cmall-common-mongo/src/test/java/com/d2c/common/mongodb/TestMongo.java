package com.d2c.common.mongodb;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.junit.Test;

import java.util.Set;

public class TestMongo {

    @Test
    public void testMongodb() {
        try {
            // 连接到 mongodb 服务
            MongoClient mongo = new MongoClient("192.168.0.145", 27017);
            // 根据mongodb数据库的名称获取mongodb对象 ,
            DB db = mongo.getDB("mydb");
            Set<String> collectionNames = db.getCollectionNames();
            // 打印出test中的集合
            for (String name : collectionNames) {
                System.out.println("collectionName===" + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
