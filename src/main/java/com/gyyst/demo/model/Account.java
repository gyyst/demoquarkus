package com.gyyst.demo.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

@Data
@Table("account")  // 将实体映射到 account 表:contentReference[oaicite:8]{index=8}
public class Account {
    @Id(keyType = KeyType.Auto)  // 主键自增
    private Long id;
    private String userName;
    private Integer age;
}
