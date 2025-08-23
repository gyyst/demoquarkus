package com.gyyst.demo.model;

import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.gyyst.demo.model.proxy.User1Proxy;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/5/28 13:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
@EntityProxy
public class User1 implements ProxyEntityAvailable<User1, User1Proxy> {
    @Id(keyType = KeyType.Auto)
    private Long id;

    private String name;
}
