package com.wk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HiveQueryService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> queryHiveData(String catalog, String schema, String table) {
        // 切换到指定的 Catalog
//        jdbcTemplate.execute("USE CATALOG " + catalog);

        // 查询指定 Schema 和 Table 的数据
        String sql = "SELECT * FROM " + catalog + "." + schema + "." + table;
        return jdbcTemplate.queryForList(sql);
    }
}
