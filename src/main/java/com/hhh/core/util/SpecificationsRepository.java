package com.hhh.core.util;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 动态条件查询接口，继承了CURD操作
 * @author 3hhjj
 *
 * @param <T>
 * @param <ID>
 */

@NoRepositoryBean
public interface SpecificationsRepository<T, ID extends Serializable> extends CrudRepository<T, Serializable>, JpaSpecificationExecutor<T>, JpaRepository<T, Serializable>{


}
