package com.gyyst.demo.config;

import com.gyyst.demo.mapper.AccountMapper;
import com.gyyst.demo.model.Account;
import com.mybatisflex.core.MybatisFlexBootstrap;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import javax.sql.DataSource;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/5/28 13:10
 */
@ApplicationScoped
public class MybatisConfig {
    @Inject
    DataSource dataSource;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("初始化MybatisFlexBootstrap");
        MybatisFlexBootstrap bootstrap = MybatisFlexBootstrap.getInstance()
                .setDataSource(dataSource)
//                .setLogImpl(StdOutImpl.class)
                .addMapper(AccountMapper.class)
                .start();
        AccountMapper accountMapper = bootstrap.getMapper(AccountMapper.class);

        Account account = accountMapper.selectOneById(1);
        account.setAge(10);
        account.setUserName("asd");
        accountMapper.update(account);
        System.out.println(account);
    }

    void onStop(@Observes ShutdownEvent ev) {

    }


}
