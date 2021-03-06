/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mingcai.edu.modules.oa.entity.contract;

import org.hibernate.validator.constraints.Length;

import com.mingcai.edu.common.persistence.DataEntity;

/**
 * 销售产品信息Entity
 * @author 坤
 * @version 2018-01-09
 */
public class OaContractProduct extends DataEntity<OaContractProduct> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String specifications;		// 规格
	private String type;		// 产品类型，自有，开发，采购
	private String contract_id;		// 合同id
	public OaContractProduct() {
		super();
	}

	public OaContractProduct(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=1000, message="规格长度必须介于 0 和 1000 之间")
	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	@Length(min=1, max=64, message="产品类型，自有，开发，采购长度必须介于 1 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContract_id() {
		return contract_id;
	}

	public void setContract_id(String contract_id) {
		this.contract_id = contract_id;
	}
}