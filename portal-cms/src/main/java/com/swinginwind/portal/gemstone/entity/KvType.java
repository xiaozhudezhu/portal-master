package com.swinginwind.portal.gemstone.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.swinginwind.portal.common.entity.MysqlSequenceIdEntity;

@Entity
@Table(name = "kv_type")
public class KvType extends MysqlSequenceIdEntity implements Serializable {

	private static final long serialVersionUID = 4154575436389807961L;
		
	private String name;
		
	private Integer orderNo;
		
	private String type;
	
	private String comments;

	private Set<Kv> children;

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

	/**
	 * @return the children
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "typeId")
	@OrderBy("orderNo,id")
	public Set<Kv> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<Kv> children) {
		this.children = children;
	}

}
