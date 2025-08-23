package com.gyyst.demo.controller;


import com.gyyst.demo.mapper.AccountMapper;
import com.gyyst.demo.model.Account;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    AccountMapper accountMapper;  // 注入 MyBatis-Flex Mapper

    @GET
    public List<Account> listAll() {
        return accountMapper.selectListByQuery(new QueryWrapper());  // 查询所有账户
    }

    @GET
    @Path("/{id}")
    public Account get(@PathParam("id") Long id) {
        Account account = accountMapper.selectOneById(id);
        return account;
    }

    @POST
    public Account create(Account acct) {
        accountMapper.insert(acct);
        return acct;
    }

    @PUT
    @Path("/{id}")
    public Account update(@PathParam("id") Long id, Account acct) {
        acct.setId(id);
        accountMapper.update(acct);
        return acct;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        accountMapper.deleteById(id);
    }
}
