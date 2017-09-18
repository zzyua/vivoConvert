package com.boot.entity;

import java.util.Date;

public class Sales_volume {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Sales_volume(){
		super();
	}
	
//	@Id
//	@GeneratedValue     //自增属性
	private Integer id;
	
	/**
	 * 类别
	 * 【总】、【vivo】、【oppo】
	 */
//	@NotNull(message="类别不能为空")
	private String classcation;

	/**
	 * yyyy-MM保存，展示需要显示成yyyy年MM月
	 */
//	@NotNull(message="年月不能为空")
	private String date ;
	
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
//	@NotNull(message="日期不能为空")
	private String datatime;
	
	
	/**
	 * 数量
	 */
//	@NotNull(message="销售数量不能为空")
	private Integer amount;
	
	/**
	 * 地区name
	 */
//	@NotNull(message="地区不能为空")
	private String regioname;
	
	/**
	 * 修改日期
	 */
	//@NotNull(message="修改日期不能为空")
	private Date modifydate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClasscation() {
		return classcation;
	}

	public void setClasscation(String classcation) {
		this.classcation = classcation;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDatatime() {
		return datatime;
	}

	public void setDatatime(String datatime) {
		this.datatime = datatime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	
	public String getRegioname() {
		return regioname;
	}

	public void setRegioname(String regioname) {
		this.regioname = regioname;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	@Override
	public String toString() {
		return "Sales_volume [id=" + id + ", classcation=" + classcation + ", date=" + date + ", datatime=" + datatime
				+ ", amount=" + amount + ", regioname=" + regioname + ", modifydate=" + modifydate + "]";
	}
	
	
	
	


}
