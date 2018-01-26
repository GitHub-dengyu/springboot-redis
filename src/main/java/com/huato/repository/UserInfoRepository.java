package com.huato.repository;

import com.huato.entity.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository <UserInfo,Long>{

    @Query(value = "SELECT * FROM  DemoInfo a  where a.id=?1", nativeQuery = true)
    UserInfo findDemoInfo(Long id);
}
