package com.swinginwind.portal.gemstone.dto;
import com.swinginwind.portal.common.dto.CommonQueryDTO;

/**
 * 后台报告查询信息封装
 */
public class GemstoneReportQueryDTO extends CommonQueryDTO{
	
	private String no;
	
	private String type;
	
	private String typeName;

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}



}
