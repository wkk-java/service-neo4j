package com.wk.entity;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.Values;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public class Neo4jRelationImport {

    public static void main(String[] args) {
        // 连接到Neo4j数据库
        var driver = GraphDatabase.driver("bolt://192.168.50.132:7687", AuthTokens.basic("neo4j", "neo4jadmin"));

        try (Session session = driver.session()) {
            // 准备关系数据
            List<Map<String, Object>> relationData = prepareRelationData();

            // 开始事务
            Transaction tx = session.beginTransaction();

            // 使用UNWIND批量创建关系
            tx.run("UNWIND $data AS row " +
                   "MATCH (source:Person { id: row.sourceName }), (target:Person {id: row.targetName })" +
                            " CREATE (source)-[:FRIENDS]->(target)",
                   Values.parameters("data", relationData));
            // 提交事务
            tx.commit();
        } finally {
            driver.close();
        }
        log.info("关系导入完毕");
    }

    private static List<Map<String, Object>> prepareRelationData() {
        List<Map<String, Object>> relationData = new LinkedList<>();

        for (int i = 0; i < 1000; i++) {
            Map<String, Object> relation1 = new HashMap<>();
            relation1.put("sourceName",  i);
            relation1.put("targetName", (i +2));
            relation1.put("label", "FRIENDS");
            relation1.put("since", i);
            relationData.add(relation1);
        }
        return relationData;
    }
}
