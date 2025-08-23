package com.gyyst.demo.controller;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.gyyst.demo.mapper.UserMapper;
import com.gyyst.demo.model.User;
import com.gyyst.demo.model.User1;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/5/28 13:23
 */
@Path("/user")
public class UserController {
    @Inject
    UserMapper userMapper;
    @Inject
    EasyEntityQuery easyEntityQuery;

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User1 getUser(@PathParam("id") Long id) {
        User1 user1 = easyEntityQuery.queryable(User1.class).where(user -> {
            user.id().eq(id);
        }).singleOrNull();
        return user1;
//        return userMapper.getUser(id);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Integer createUser(@FormParam("id") Long id, @FormParam("name") String name) {
//        User user = new User();
//        user.setId(id);
//        user.setName(name);
//        return userMapper.insert(user);
        return userMapper.createUser(id, name);
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public Integer removeUser(@PathParam("id") Long id) {
        return userMapper.removeUser(id);
    }
}
