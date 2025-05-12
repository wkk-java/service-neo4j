package com.wk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HiveQueryController {

    @Autowired
    private HiveQueryService hiveQueryService;

    // http://localhost:8081/query?catalog=hive_catalog&schema=test_db&table=example_table1_0821
    @GetMapping("/query")
    public List<Map<String, Object>> queryHiveData(
            @RequestParam String catalog,
            @RequestParam String schema,
            @RequestParam String table) {
        return hiveQueryService.queryHiveData(catalog, schema, table);
    }
}
