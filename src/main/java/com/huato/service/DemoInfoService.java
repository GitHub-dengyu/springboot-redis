package com.huato.service;

import com.huato.entity.UserInfo;

public interface DemoInfoService {

    UserInfo findById(long id);
    void deleteFromCache(long id);
    void test();
}
