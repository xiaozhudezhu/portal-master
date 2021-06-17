package com.swinginwind.portal.gemstone.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.swinginwind.portal.common.entity.MysqlSequenceIdEntity;

@Entity
@Table(name = "kv")
public class Kv extends MysqlSequenceIdEntity implements Serializable {

	private static final long serialVersionUID = 4154575436389807961L;
		
	private String name;
		
	private Integer orderNo;
	
	private String typeId;
	
	private String type;
	
	private String comments;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
