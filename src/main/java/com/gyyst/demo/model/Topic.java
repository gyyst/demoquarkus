package com.gyyst.demo.model;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/6/27 21:06
 */
@Data
@Table("t_topic")
@EntityProxy
public class Topic {
    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}