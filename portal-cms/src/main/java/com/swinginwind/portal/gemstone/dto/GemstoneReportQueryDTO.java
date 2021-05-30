package com.swinginwind.portal.gemstone.dto;
import com.swinginwind.portal.common.dto.CommonQueryDTO;

/**
 * 后台报告查询信息封装
 */
public class GemstoneReportQueryDTO extends CommonQueryDTO{
	
	private String no;
	
	private String type;

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



}
