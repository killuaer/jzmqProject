package com.hhh.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hhh.auth.entity.JcXmcsInfo;

@Repository
public interface JcXmcsInfoDao extends CrudRepository<JcXmcsInfo,String>,
				PagingAndSortingRepository<JcXmcsInfo, String>, JpaSpecificationExecutor<JcXmcsInfo>{
	
	@Query("select e from JcXmcsInfo e ")
	public List<JcXmcsInfo> findAlllist();
	
	@Query("select e from JcXmcsInfo e where parentId=?1 ")
	public List<JcXmcsInfo> findByParentId(String parentId);
}
