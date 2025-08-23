package com.gyyst.demo.config;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import javax.sql.DataSource;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/6/6 21:34
 */

@ApplicationScoped
public class EasyQueryConfig {
    @Inject
    DataSource dataSource;

    @ApplicationScoped
    @Produces
    public EasyEntityQuery easyEntityQuery() {
        return new DefaultEasyEntityQuery(EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    //进行一系列可以选择的配置
                    op.setPrintSql(true);
                    op.setDeleteThrowError(false);
                })
                .useDatabaseConfigure(new SQLiteDatabaseConfiguration())
                .build());
    }
}
