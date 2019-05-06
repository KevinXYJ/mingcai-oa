/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mingcai.edu.modules.oa.dao;

import com.mingcai.edu.common.persistence.CrudDao;
import com.mingcai.edu.common.persistence.annotation.MyBatisDao;
import com.mingcai.edu.modules.oa.entity.OaFinanceCompany;

/**
 * 报销单位管理DAO接口
 * @author 江坤
 * @version 2017-12-12
 */
@MyBatisDao
public interface OaFinanceCompanyDao extends CrudDao<OaFinanceCompany> {
	
}