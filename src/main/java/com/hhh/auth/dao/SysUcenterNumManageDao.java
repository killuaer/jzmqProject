package com.hhh.auth.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hhh.auth.entity.SysUcenterNumManage;

@Repository
@Transactional
public interface SysUcenterNumManageDao extends CrudRepository<SysUcenterNumManage,String>,
				PagingAndSortingRepository<SysUcenterNumManage, String>, JpaSpecificationExecutor<SysUcenterNumManage>{
	
	@Query("select e from SysUcenterNumManage e ")
	public List<SysUcenterNumManage> findAlllist();
	
	@Query("select e from SysUcenterNumManage e where bhId=?1 ")
	public SysUcenterNumManage findBybhId(String bhId);
}
