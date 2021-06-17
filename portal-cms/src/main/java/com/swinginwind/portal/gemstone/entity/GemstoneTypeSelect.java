package com.swinginwind.portal.gemstone.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.swinginwind.portal.common.entity.MysqlSequenceIdEntity;

@Entity
@Table(name = "gemstone_type")
public class GemstoneTypeSelect implements Serializable {

	private static final long serialVersionUID = 4154575436389807961L;
	
	private String value;
	
	private String label;
	
	private String parentId;
	
	private String orderNo;
	
	private String code;
					
	private Set<GemstoneTypeSelect> children;

	/**
	 * @return the children
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId")
	@OrderBy("order_no,code")
	public Set<GemstoneTypeSelect> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<GemstoneTypeSelect> children) {
		this.children = children;
	}

	/**
	 * @return the value
	 */
	@Id
	@Column(name = "id")
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the label
	 */
	@Column(name = "name")
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
}
