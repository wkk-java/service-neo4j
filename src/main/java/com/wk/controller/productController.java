package com.wk.controller;


import com.wk.entity.ProductInfo;
import com.wk.repository.ProductInfoRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.data.neo4j.core.Neo4jOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 产品数据
 * @Date:   2024-04-01
 * @author wk
 * @Version: V1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Slf4j
public class productController {

	private final ProductInfoRespository productInfoRespository;

	private final Driver driver;

	private final Neo4jOperations neo4jOperations;

	@GetMapping
	public String testBatchSave() {
		List<ProductInfo> productInfos = new ArrayList<>();
		for (int i = 0; i < 5000; i++) {
			ProductInfo build = ProductInfo.builder()
					.batchNumber("batchNumber_" + i)
					.code("code_" + i)
					.parentCode("pCode_" + i)
					.build();
			productInfos.add(build);
		}
		neo4jOperations.saveAll(productInfos);
		return  "保存完成";
	}

	@GetMapping(value = "/listProduct")
	public List<ProductInfo> listProduct() {
//		List<ProductInfo> productInfoList = neo4jOperations.findAll("match(n:ProductInfo) return n", ProductInfo.class);

		List<ProductInfo> productInfos = productInfoRespository.listAll();
		log.info("查询列表数据：{}", productInfos);
		List<Object> objects = productInfoRespository.listAllObjs();
		log.info("查询列表数据：{}", objects);
		return productInfos;
	}

	@GetMapping(value = "/batchImport")
	public String batchImport() {
		List<ProductInfo> productInfos = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			ProductInfo build = ProductInfo.builder()
					.batchNumber("batch_no_" + i)
					.code("figure_no_" + i)
					.parentCode("parent_figure_no_" + i)
					.build();
			productInfos.add(build);
		}

		int batchSize = 100;

		Field[] declaredFields = ProductInfo.class.getDeclaredFields();
		StringBuffer buffer = new StringBuffer();
		for (Field declaredField : declaredFields) {
			buffer.append("\""+declaredField.getName() + "\":row."+declaredField.getName()).append(",");
		}
		String sql = buffer.substring(0, buffer.length() - 1);

		try (Session session = driver.session()) {
			for (int i = 0; i < productInfos.size(); i += batchSize) {
				int end = Math.min(i + batchSize, productInfos.size());
				List<ProductInfo> batch = productInfos.subList(i, end);

				String createNodesQuery = "UNWIND $data AS row CREATE (n:DataTable { " + sql + "})";
				Map<String, Object> params = new HashMap<>();
				params.put("data", batch);

				session.writeTransaction(tx -> tx.run(createNodesQuery, params));
			}
		}
		log.info("导入完成");
		return "导入完成";
	}


}
