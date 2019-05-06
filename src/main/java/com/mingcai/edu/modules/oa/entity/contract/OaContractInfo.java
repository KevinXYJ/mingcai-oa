/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.mingcai.edu.modules.oa.entity.contract;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import com.mingcai.edu.common.persistence.DataEntity;

/**
 * 合同管理Entity
 * @author 坤
 * @version 2018-01-10
 */
public class OaContractInfo extends DataEntity<OaContractInfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 单位名称
	private String productName;		// 项目名称
	private String contacts;		// 销售人员
	private String userName;		// 销售人员
	private String contactNumber;		// 联系电话
	private String bankAccount;		// 银行账号
	private String dutyParagraph;		// 税号
	private String type;		// 合同类型
	private Date timeNode;		// 验收时间节点
	private String status;		// 付款完成状态
	private String finProductId;		// 项目id
	private String subject;//合同主体
	private String openingBank;//开户行

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}

	public OaContractInfo() {
		super();
	}

	public OaContractInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="单位名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Length(min=1, max=100, message="销售人员长度必须介于 1 和 100 之间")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	
	@Length(min=0, max=100, message="联系电话长度必须介于 0 和 100 之间")
	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@Length(min=0, max=100, message="银行账号长度必须介于 0 和 100 之间")
	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	@Length(min=0, max=100, message="税号长度必须介于 0 和 100 之间")
	public String getDutyParagraph() {
		return dutyParagraph;
	}

	public void setDutyParagraph(String dutyParagraph) {
		this.dutyParagraph = dutyParagraph;
	}
	
	@Length(min=0, max=100, message="合同类型长度必须介于 0 和 100 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTimeNode() {
		return timeNode;
	}

	public void setTimeNode(Date timeNode) {
		this.timeNode = timeNode;
	}
	

	@Length(min=0, max=10, message="付款完成状态长度必须介于 0 和 10 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=64, message="项目id长度必须介于 0 和 64 之间")
	public String getFinProductId() {
		return finProductId;
	}

	public void setFinProductId(String finProductId) {
		this.finProductId = finProductId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}