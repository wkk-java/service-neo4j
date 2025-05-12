package com.wk.repository;

import com.wk.entity.ProductInfo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRespository extends Neo4jRepository<ProductInfo, Long> {

   @Query("MATCH (n:ProductInfo) RETURN n")
    List<ProductInfo> listAll();

    @Query("MATCH (n:product) RETURN n")
    List<Object> listAllObjs();
}
