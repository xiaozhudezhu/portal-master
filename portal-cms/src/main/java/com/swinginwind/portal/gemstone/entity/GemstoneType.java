package com.swinginwind.portal.gemstone.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.swinginwind.portal.common.entity.MysqlSequenceIdEntity;

@Entity
@Table(name = "gemstone_type")
public class GemstoneType extends MysqlSequenceIdEntity implements Serializable {

	private static final long serialVersionUID = 4154575436389807961L;
	
	private String code;
	
	private String name;
	
	private String parentId;
	
	private Integer orderNo;
	
	private String comments;
	
	private Set<GemstoneType> children;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the children
	 */
	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentId")
	@OrderBy("orderNo,code")
	public Set<GemstoneType> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<GemstoneType> children) {
		this.children = children;
	}
	
}
