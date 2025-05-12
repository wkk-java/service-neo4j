package com.wk.entity;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;

import java.util.*;

@Slf4j
public class Neo4jDataImport {

    public static void main(String[] args) {
        // 连接到Neo4j数据库
        var driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "neo4jadmin"));

        try (Session session = driver.session()) {
            // 准备数据
            List<Map<String, Object>> dataList = prepareData();

            // 开始事务
            Transaction tx = session.beginTransaction();

            // 使用UNWIND批量创建节点
            tx.run("UNWIND $data AS row CREATE (n:Person1 { name: row.name, age: row.age, city: row.city })",
                   Values.parameters("data", dataList));

            // 提交事务
            tx.commit();
        } finally {
            driver.close();
        }
        log.info("导入完毕");
    }

    private static List<Map<String, Object>> prepareData() {
        // 假设你已经读取了数据并存储在一个List中
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("name", "name"+ i);
            data.put("age",  i);
            data.put("city", "city_"+i);
            dataList.add(data);
        }

        return dataList;
    }
}
