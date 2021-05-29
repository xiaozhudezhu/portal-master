package com.swinginwind.portal.gemstone.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.swinginwind.portal.common.entity.MysqlSequenceIdEntity;

@Entity
@Table(name = "gemstone_report_image")
public class GemstoneReportImage extends MysqlSequenceIdEntity implements Serializable {

	private static final long serialVersionUID = 4154575436389807961L;
	
	private String reportId;
	
	private String fileName;
	
	private String filePath;
	
	private Integer orderNo;

	/**
	 * @return the reportId
	 */
	@Column(name = "report_id")
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the fileName
	 */
	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the orderNo
	 */
	@Column(name = "order_no")
	public Integer getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

}
