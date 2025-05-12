package com.wk.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.neo4j.driver.Values.parameters;

@Slf4j
public class Neo4jDataImport {

    public static void main(String[] args) {

        // 连接到Neo4j数据库
        var driver = GraphDatabase.driver("bolt://192.168.50.132:7687", AuthTokens.basic("neo4j", "123456"));
        
        try (Session session = driver.session()) {
            Transaction tx = session.beginTransaction();
            
            // 假设你已经读取了数据并存储在一个List中
            List<Map<String, Object>> dataList = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Map<String, Object> data = new HashMap<>();
                data.put("id",  i);
                data.put("name", "name"+ i);
                data.put("age",  i);
                data.put("city", "city_"+i);
                dataList.add(data);
            }
            
            for (Map<String, Object> row : dataList) {
                String name = (String) row.get("name");
                int id = (int) row.get("id");
                int age = (int) row.get("age");
                String city = (String) row.get("city");
                
                // 执行Cypher语句创建节点
                tx.run("CREATE (:Person {id:$id, name: $name, age: $age, city: $city })",
                       parameters("id", id, "name", name, "age", age, "city", city));
            }
            
            tx.commit();
        } finally {
            driver.close();
        }
        log.info("导入完毕");
    }


}
