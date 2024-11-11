package com.xyqq.service;

import java.util.Set;

public interface UserService {

    String getUserById(String id);

    void saveUserHash(String id, String field, String value);

    Object getUserHash(String id, String field);

    void saveUserList(String key, String value);

    Object getUserList(String key, long index);

    void saveUserSet(String key, String value);

    Set<Object> getUserSet(String key);

    void saveUserZSet(String key, String value, double score);

    Set<Object> getUserZSet(String key, double min, double max);

}
