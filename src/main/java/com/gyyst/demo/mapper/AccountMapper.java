package com.gyyst.demo.mapper;

import com.gyyst.demo.model.Account;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    // 可根据需要自定义复杂查询方法
}
