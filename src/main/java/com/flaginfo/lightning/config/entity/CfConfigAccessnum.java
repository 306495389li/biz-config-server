package com.flaginfo.lightning.config.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

import com.flaginfo.lightning.common.BaseEntity;

/**
 * @desc 接入号配置实体
 * @author zyong
 * @Tue May 16 11:21:05 CST 2017
 */
@Entity
@Table(name = "CF_CONFIG_ACCESSNUM")
public class CfConfigAccessnum extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
	/**
	 * 主键ID
	 */
	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.flaginfo.lightning.common.IdUtil")
	@Column(name = "cf_config_accessnum_id", nullable = false, length = 22)
	private Long cfConfigAccessnumId;
	/**
	 * 企业ID
	 */
	@Column(name = "ad_client_id", nullable = true, length = 22)
	private Long adClientId;
	/**
	 * 名称
	 */
	@Column(name = "cf_name", nullable = true, length = 50)
	private String cfName;
	/**
	 * 运营商
	 */
	@Column(name = "carrier", nullable = true, length = 22)
	private Long carrier;
	/**
	 * 省份区分
	 */
	@Column(name = "province_division", nullable = true, length = 22)
	private Long provinceDivision;
	/**
	 * 接入号
	 */
	@Column(name = "accessnum", nullable = true, length = 22)
	private String accessnum;
	/**
	 * 描述
	 */
	@Column(name = "description", nullable = true, length = 200)
	private String description;
	/**
	 * 排序
	 */
	@Column(name = "line_no", nullable = true, length = 22)
	private Long lineNo;
   
    public Long getCfConfigAccessnumId() {
       return cfConfigAccessnumId;
    }
    
	public void setCfConfigAccessnumId(Long cfConfigAccessnumId) {
       this.cfConfigAccessnumId = cfConfigAccessnumId;
    }
   
    public Long getAdClientId() {
       return adClientId;
    }
    
	public void setAdClientId(Long adClientId) {
       this.adClientId = adClientId;
    }
   
    public String getCfName() {
       return cfName;
    }
    
	public void setCfName(String cfName) {
       this.cfName = cfName;
    }
   
    public Long getCarrier() {
       return carrier;
    }
    
	public void setCarrier(Long carrier) {
       this.carrier = carrier;
    }
   
    public Long getProvinceDivision() {
       return provinceDivision;
    }
    
	public void setProvinceDivision(Long provinceDivision) {
       this.provinceDivision = provinceDivision;
    }
   
    public String getAccessnum() {
       return accessnum;
    }
    
	public void setAccessnum(String accessnum) {
       this.accessnum = accessnum;
    }
   
    public String getDescription() {
       return description;
    }
    
	public void setDescription(String description) {
       this.description = description;
    }
   
    public Long getLineNo() {
       return lineNo;
    }
    
	public void setLineNo(Long lineNo) {
       this.lineNo = lineNo;
    }
  
	@Override
	public Long getPrimaryKey() {
		return cfConfigAccessnumId;
	}
}
