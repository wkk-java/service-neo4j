package com.wk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Node(value = "ProductInfo")
@Data
public class ProductInfo implements Serializable {
    @Id
    @GeneratedValue
    private long _id;
    @Property
    private String id;
    @Property
    private String pid;
    @Property("batch_number")
    private String batchNumber;
    @Property("product_name")
    private String productName;
    @Property("code")
    private String code;
    @Property("parent_code")
    private String parentCode;

    @Property("startNodeId")
    private long startNodeId;

    @Property("endNodeId")
    private long endNodeId;
}

