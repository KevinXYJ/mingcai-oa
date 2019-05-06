/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mingcai.edu.modules.oa.dao.crm;

import com.mingcai.edu.common.persistence.CrudDao;
import com.mingcai.edu.common.persistence.annotation.MyBatisDao;
import com.mingcai.edu.modules.oa.entity.crm.OaCrmProduct;

import java.util.List;

/**
 * 项目报备DAO接口
 * @author 坤
 * @version 2018-02-07
 */
@MyBatisDao
public interface OaCrmProductDao extends CrudDao<OaCrmProduct> {
	public List<OaCrmProduct> findOpenList(OaCrmProduct oaCrmProduct);
	public List<OaCrmProduct> findApplyList(OaCrmProduct oaCrmProduct);
}