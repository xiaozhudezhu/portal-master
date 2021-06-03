package com.swinginwind.portal.gemstone.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.swinginwind.portal.common.entity.BaseEntity;
import com.swinginwind.portal.org.entity.User;

@Entity
@Table(name = "gemstone_report")
public class GemstoneReport extends BaseEntity {

	private static final long serialVersionUID = 4154575436389807961L;
	
	private String type;
	
	private String no;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportDate;
	
	private String object;
	
	private String identification;
	
	private Double weight;
	
	private Double dimensionsLength;
	
	private Double dimensionsWidth;
	
	private Double dimensionsHeight;
	
	private String color;
	
	private String cut;
	
	private String shape;
	
	private String origin;
	
	private String comments;
	
	private String colorGrade;
	
	private String clarityGrade;
	
	private String proportions;
	
	private String polish;
	
	private String symmetry;
	
	private String fluorescence;
	
	private String clarityFeature;
	
	private User createUser;
	
	private User updateUser;
	
	private Set<GemstoneReportImage> images;

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "no")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name = "report_date")
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "object")
	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	@Column(name = "identification")
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@Column(name = "weight")
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Column(name = "dimensions_length")
	public Double getDimensionsLength() {
		return dimensionsLength;
	}

	public void setDimensionsLength(Double dimensionsLength) {
		this.dimensionsLength = dimensionsLength;
	}

	@Column(name = "dimensions_width")
	public Double getDimensionsWidth() {
		return dimensionsWidth;
	}

	public void setDimensionsWidth(Double dimensionsWidth) {
		this.dimensionsWidth = dimensionsWidth;
	}

	@Column(name = "dimensions_height")
	public Double getDimensionsHeight() {
		return dimensionsHeight;
	}

	public void setDimensionsHeight(Double dimensionsHeight) {
		this.dimensionsHeight = dimensionsHeight;
	}

	@Column(name = "color")
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name = "cut")
	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
	}

	@Column(name = "origin")
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	@Column(name = "comments")
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Column(name = "color_grade")
	public String getColorGrade() {
		return colorGrade;
	}

	public void setColorGrade(String colorGrade) {
		this.colorGrade = colorGrade;
	}

	@Column(name = "clarity_grade")
	public String getClarityGrade() {
		return clarityGrade;
	}

	public void setClarityGrade(String clarityGrade) {
		this.clarityGrade = clarityGrade;
	}

	@Column(name = "proportions")
	public String getProportions() {
		return proportions;
	}

	public void setProportions(String proportions) {
		this.proportions = proportions;
	}

	@Column(name = "polish")
	public String getPolish() {
		return polish;
	}

	public void setPolish(String polish) {
		this.polish = polish;
	}

	@Column(name = "symmetry")
	public String getSymmetry() {
		return symmetry;
	}

	public void setSymmetry(String symmetry) {
		this.symmetry = symmetry;
	}

	@Column(name = "fluorescence")
	public String getFluorescence() {
		return fluorescence;
	}

	public void setFluorescence(String fluorescence) {
		this.fluorescence = fluorescence;
	}

	@Column(name = "clarity_feature")
	public String getClarityFeature() {
		return clarityFeature;
	}

	public void setClarityFeature(String clarityFeature) {
		this.clarityFeature = clarityFeature;
	}

	@ManyToOne
	@JoinColumn(name = "create_user")
	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}
	@ManyToOne
	@JoinColumn(name = "update_user")
	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "report_id")
	@OrderBy("order_no")
	public Set<GemstoneReportImage> getImages() {
		return images;
	}

	public void setImages(Set<GemstoneReportImage> images) {
		this.images = images;
	}

	/**
	 * @return the shape
	 */
	@Column(name = "shape")
	public String getShape() {
		return shape;
	}

	/**
	 * @param shape the shape to set
	 */
	public void setShape(String shape) {
		this.shape = shape;
	}
	
	
}
