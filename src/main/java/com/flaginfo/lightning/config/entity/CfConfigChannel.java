package com.flaginfo.lightning.config.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

import com.flaginfo.lightning.common.BaseEntity;

/**
 * @desc 通道配置实体
 * @author zyong
 * @Tue May 16 11:21:04 CST 2017
 */
@Entity
@Table(name = "CF_CONFIG_CHANNEL")
public class CfConfigChannel extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
	/**
	 * 主键ID
	 */
	@Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "com.flaginfo.lightning.common.IdUtil")
	@Column(name = "cf_config_channel_id", nullable = false, length = 22)
	private Long cfConfigChannelId;
	/**
	 * 企业ID
	 */
	@Column(name = "ad_client_id", nullable = true, length = 22)
	private Long adClientId;
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
	 * 通道区分
	 */
	@Column(name = "channel_division", nullable = true, length = 22)
	private Long channelDivision;
	/**
	 * 具体接入号信息ID
	 */
	@Column(name = "cf_config_accessnum_id", nullable = true, length = 22)
	private Long cfConfigAccessnumId;
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
   
    public Long getCfConfigChannelId() {
       return cfConfigChannelId;
    }
    
	public void setCfConfigChannelId(Long cfConfigChannelId) {
       this.cfConfigChannelId = cfConfigChannelId;
    }
   
    public Long getAdClientId() {
       return adClientId;
    }
    
	public void setAdClientId(Long adClientId) {
       this.adClientId = adClientId;
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
   
    public Long getChannelDivision() {
       return channelDivision;
    }
    
	public void setChannelDivision(Long channelDivision) {
       this.channelDivision = channelDivision;
    }
   
    public Long getCfConfigAccessnumId() {
       return cfConfigAccessnumId;
    }
    
	public void setCfConfigAccessnumId(Long cfConfigAccessnumId) {
       this.cfConfigAccessnumId = cfConfigAccessnumId;
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
		return cfConfigChannelId;
	}
}
