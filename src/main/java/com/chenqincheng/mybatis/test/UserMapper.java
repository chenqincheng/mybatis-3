package com.chenqincheng.mybatis.test;

public interface UserMapper {
    User selectUserById(Integer id);
    User selectUserByName(String name);
}
