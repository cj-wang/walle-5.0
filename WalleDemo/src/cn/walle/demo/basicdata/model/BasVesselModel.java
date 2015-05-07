package cn.walle.demo.basicdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.walle.framework.core.model.BaseModelClass;

/**
 * Model class for 船舶信息
 */
@Entity
@Table(name = "BAS_VESSEL")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BasVesselModel extends BaseModelClass {

	private static final long serialVersionUID = 1L;

	public static final String MODEL_NAME = "BasVessel";

	public static final class FieldNames {
		/**
		 * Phisical
		 */
		public static final String basVesselInfoId = "basVesselInfoId";
		/**
		 * 船舶代码
		 */
		public static final String vesselCode = "vesselCode";
		/**
		 * 英文名称
		 */
		public static final String vesselName = "vesselName";
		/**
		 * 中文名称
		 */
		public static final String vesselNameCn = "vesselNameCn";
		/**
		 * 开始代理日期
		 */
		public static final String agentStartDate = "agentStartDate";
		/**
		 * 终止代理日期
		 */
		public static final String agentEndDate = "agentEndDate";
		/**
		 * 国家代码
		 */
		public static final String country = "country";
		/**
		 * 净重吨
		 */
		public static final String netTons = "netTons";
		/**
		 * 载重吨
		 */
		public static final String deadweightTons = "deadweightTons";
		/**
		 * 船舶种类
		 */
		public static final String vesselCategory = "vesselCategory";
		/**
		 * 船舶所属性质
		 */
		public static final String vesselOwnership = "vesselOwnership";
		/**
		 * 海关监管簿编号
		 */
		public static final String customBookNo = "customBookNo";
		/**
		 * 海关监管簿船舶代码
		 */
		public static final String customVesselNo = "customVesselNo";
		/**
		 * 海关监管簿内河编码
		 */
		public static final String customInlandNo = "customInlandNo";
		/**
		 * 船东代码
		 */
		public static final String vesselOwnerCode = "vesselOwnerCode";
		/**
		 * 船东公司名称
		 */
		public static final String vesselOwnerName = "vesselOwnerName";
		/**
		 * 船东公司地址
		 */
		public static final String vesselOwnerAddress = "vesselOwnerAddress";
		/**
		 * 船东联系人
		 */
		public static final String vesselOwnerContact = "vesselOwnerContact";
		/**
		 * 船东联系人电话
		 */
		public static final String vesselOwnerTel = "vesselOwnerTel";
		/**
		 * 船上联系人
		 */
		public static final String vesselOnboardContact = "vesselOnboardContact";
		/**
		 * 船上联系人电话(HK)
		 */
		public static final String vesselOnboardTelHk = "vesselOnboardTelHk";
		/**
		 * 船上联系人电话(国内)
		 */
		public static final String vesselOnboardTelIl = "vesselOnboardTelIl";
		/**
		 * 船长名称
		 */
		public static final String vesselCaptain = "vesselCaptain";
		/**
		 * 香港海关编号
		 */
		public static final String customCodeHk = "customCodeHk";
		/**
		 * 船籍港
		 */
		public static final String portOriginRegistered = "portOriginRegistered";
		/**
		 * 船舶建造日期
		 */
		public static final String dateMade = "dateMade";
		/**
		 * 船长(M)
		 */
		public static final String length = "length";
		/**
		 * 船宽(M)
		 */
		public static final String width = "width";
		/**
		 * 船高(M)
		 */
		public static final String height = "height";
		/**
		 * 主机数目
		 */
		public static final String motorQty = "motorQty";
		/**
		 * 主机功率
		 */
		public static final String mainMotorPower = "mainMotorPower";
		/**
		 * 主机耗油标准(每小时耗油KG)
		 */
		public static final String mainMotorOil = "mainMotorOil";
		/**
		 * 副机功率
		 */
		public static final String backupMotorPower = "backupMotorPower";
		/**
		 * 副机耗油标准(每小时KG)
		 */
		public static final String backupMotorOil = "backupMotorOil";
		/**
		 * 担数（船舶总功率的指标数）
		 */
		public static final String vesselPowerTotal = "vesselPowerTotal";
		/**
		 * 总吨
		 */
		public static final String tonTotal = "tonTotal";
		/**
		 * 净吨
		 */
		public static final String tonNet = "tonNet";
		/**
		 * 载重吨
		 */
		public static final String tonLoad = "tonLoad";
		/**
		 * 总TEU
		 */
		public static final String teuTotal = "teuTotal";
		/**
		 * 吉TEU
		 */
		public static final String teuEmpty = "teuEmpty";
		/**
		 * 重TEU
		 */
		public static final String teuFull = "teuFull";
		/**
		 * CMO编号
		 */
		public static final String cmo = "cmo";
		/**
		 * 吃水深度
		 */
		public static final String waterBelow = "waterBelow";
		/**
		 * 操作及适航
		 */
		public static final String condition = "condition";
		/**
		 * 特殊情况
		 */
		public static final String special = "special";
		/**
		 * 船上所载危险品
		 */
		public static final String danger = "danger";
		/**
		 * 香港停靠码头
		 */
		public static final String pierHk = "pierHk";
		/**
		 * 香港牌照号码
		 */
		public static final String hkLicense = "hkLicense";
		/**
		 * 外地注册号码
		 */
		public static final String officialNo = "officialNo";
		/**
		 * 边防备案号
		 */
		public static final String borderRecord = "borderRecord";
		/**
		 * 船舶证书
		 */
		public static final String shipCertificate = "shipCertificate";
		/**
		 * 海关备案号
		 */
		public static final String customsRecord = "customsRecord";
		/**
		 * 控制字
		 */
		public static final String controlWord = "controlWord";
		/**
		 * 备注
		 */
		public static final String remark = "remark";
		/**
		 * 有效
		 */
		public static final String active = "active";
		/**
		 * 航线种类ID
		 */
		public static final String basLineCategoryId = "basLineCategoryId";
		/**
		 * 船检登记号
		 */
		public static final String shipRegisterNo = "shipRegisterNo";
		/**
		 * 船舶编号
		 */
		public static final String shipNo = "shipNo";
		/**
		 * HIT
		 */
		public static final String hitId = "hitId";
		/**
		 * RTT
		 */
		public static final String rttId = "rttId";
		/**
		 * 重40
		 */
		public static final String full40 = "full40";
		/**
		 * 吉40
		 */
		public static final String empty40 = "empty40";
		/**
		 * 吉2TEU
		 */
		public static final String teuEmpty2 = "teuEmpty2";
		/**
		 * 重2TEU
		 */
		public static final String teuFull2 = "teuFull2";
		/**
		 * 租船公司
		 */
		public static final String rentVesselCompany = "rentVesselCompany";
		/**
		 * 航线类别
		 */
		public static final String routeCategory = "routeCategory";
		/**
		 * 配载技巧
		 */
		public static final String stowageSkill = "stowageSkill";
		/**
		 * 进港证费
		 */
		public static final String inPortBill = "inPortBill";
		/**
		 * parkPortBill
		 */
		public static final String parkPortBill = "parkPortBill";
		/**
		 * dragShipBill
		 */
		public static final String dragShipBill = "dragShipBill";
		/**
		 * outPortBill
		 */
		public static final String outPortBill = "outPortBill";
		/**
		 * proxyBill
		 */
		public static final String proxyBill = "proxyBill";
		/**
		 * multiPassBill
		 */
		public static final String multiPassBill = "multiPassBill";
		/**
		 * 内河编码
		 */
		public static final String riverCode = "riverCode";
		/**
		 * 税品编号
		 */
		public static final String taxNo = "taxNo";
		/**
		 * 船舶所属关区(中文)
		 */
		public static final String shipBelongCustomarea = "shipBelongCustomarea";
		/**
		 * 启用时间(危险品)
		 */
		public static final String enableDate = "enableDate";
		/**
		 * 终止时间(危险品)
		 */
		public static final String terminatDate = "terminatDate";
		/**
		 * 开始代理日期
		 */
		public static final String dateAgentStart = "dateAgentStart";
		/**
		 * 结束代理日期
		 */
		public static final String dateAgentEnd = "dateAgentEnd";
		/**
		 * 录入人
		 */
		public static final String createdByUser = "createdByUser";
		/**
		 * 创建组织
		 */
		public static final String createOffice = "createOffice";
		/**
		 * 创建时间
		 */
		public static final String createdDtmLoc = "createdDtmLoc";
		/**
		 * 创建者当地时区
		 */
		public static final String createdTimeZone = "createdTimeZone";
		/**
		 * 修改人
		 */
		public static final String updatedByUser = "updatedByUser";
		/**
		 * 修改组织
		 */
		public static final String updatedOffice = "updatedOffice";
		/**
		 * 修改时间
		 */
		public static final String updatedDtmLoc = "updatedDtmLoc";
		/**
		 * 修改者当地时区
		 */
		public static final String updatedTimeZone = "updatedTimeZone";
		/**
		 * 记录版本
		 */
		public static final String recordVersion = "recordVersion";
		/**
		 * 公司代码
		 */
		public static final String principalGroupCode = "principalGroupCode";
		/**
		 * 冻插数
		 */
		public static final String freezePlugNumber = "freezePlugNumber";
	}

	//Phisical
	private String basVesselInfoId;
	//船舶代码
	private String vesselCode;
	//英文名称
	private String vesselName;
	//中文名称
	private String vesselNameCn;
	//开始代理日期
	private Date agentStartDate;
	//终止代理日期
	private Date agentEndDate;
	//国家代码
	private String country;
	//净重吨
	private Double netTons;
	//载重吨
	private Double deadweightTons;
	//船舶种类
	private String vesselCategory;
	//船舶所属性质
	private String vesselOwnership;
	//海关监管簿编号
	private String customBookNo;
	//海关监管簿船舶代码
	private String customVesselNo;
	//海关监管簿内河编码
	private String customInlandNo;
	//船东代码
	private String vesselOwnerCode;
	//船东公司名称
	private String vesselOwnerName;
	//船东公司地址
	private String vesselOwnerAddress;
	//船东联系人
	private String vesselOwnerContact;
	//船东联系人电话
	private String vesselOwnerTel;
	//船上联系人
	private String vesselOnboardContact;
	//船上联系人电话(HK)
	private String vesselOnboardTelHk;
	//船上联系人电话(国内)
	private String vesselOnboardTelIl;
	//船长名称
	private String vesselCaptain;
	//香港海关编号
	private String customCodeHk;
	//船籍港
	private String portOriginRegistered;
	//船舶建造日期
	private Date dateMade;
	//船长(M)
	private Double length;
	//船宽(M)
	private Double width;
	//船高(M)
	private Double height;
	//主机数目
	private Integer motorQty;
	//主机功率
	private Double mainMotorPower;
	//主机耗油标准(每小时耗油KG)
	private Double mainMotorOil;
	//副机功率
	private Double backupMotorPower;
	//副机耗油标准(每小时KG)
	private Double backupMotorOil;
	//担数（船舶总功率的指标数）
	private Double vesselPowerTotal;
	//总吨
	private Double tonTotal;
	//净吨
	private Double tonNet;
	//载重吨
	private Double tonLoad;
	//总TEU
	private Integer teuTotal;
	//吉TEU
	private Integer teuEmpty;
	//重TEU
	private Double teuFull;
	//CMO编号
	private String cmo;
	//吃水深度
	private Double waterBelow;
	//操作及适航
	private String condition;
	//特殊情况
	private String special;
	//船上所载危险品
	private String danger;
	//香港停靠码头
	private String pierHk;
	//香港牌照号码
	private String hkLicense;
	//外地注册号码
	private String officialNo;
	//边防备案号
	private String borderRecord;
	//船舶证书
	private String shipCertificate;
	//海关备案号
	private String customsRecord;
	//控制字
	private String controlWord;
	//备注
	private String remark;
	//有效
	private Integer active;
	//航线种类ID
	private String basLineCategoryId;
	//船检登记号
	private String shipRegisterNo;
	//船舶编号
	private String shipNo;
	//HIT
	private String hitId;
	//RTT
	private String rttId;
	//重40
	private Long full40;
	//吉40
	private Long empty40;
	//吉2TEU
	private Integer teuEmpty2;
	//重2TEU
	private Double teuFull2;
	//租船公司
	private String rentVesselCompany;
	//航线类别
	private String routeCategory;
	//配载技巧
	private String stowageSkill;
	//进港证费
	private Double inPortBill;
	//parkPortBill
	private Double parkPortBill;
	//dragShipBill
	private Double dragShipBill;
	//outPortBill
	private Double outPortBill;
	//proxyBill
	private Double proxyBill;
	//multiPassBill
	private Double multiPassBill;
	//内河编码
	private String riverCode;
	//税品编号
	private String taxNo;
	//船舶所属关区(中文)
	private String shipBelongCustomarea;
	//启用时间(危险品)
	private Date enableDate;
	//终止时间(危险品)
	private Date terminatDate;
	//开始代理日期
	private Date dateAgentStart;
	//结束代理日期
	private Date dateAgentEnd;
	//录入人
	private String createdByUser;
	//创建组织
	private String createOffice;
	//创建时间
	private Date createdDtmLoc;
	//创建者当地时区
	private String createdTimeZone;
	//修改人
	private String updatedByUser;
	//修改组织
	private String updatedOffice;
	//修改时间
	private Date updatedDtmLoc;
	//修改者当地时区
	private String updatedTimeZone;
	//记录版本
	private Long recordVersion;
	//公司代码
	private String principalGroupCode;
	//冻插数
	private Long freezePlugNumber;

	/**
	 * Get Phisical
	 * Primary Key
	 */
	@Column(name = "BAS_VESSEL_INFO_ID")
	@Id @GeneratedValue(generator = "UUIDGenerator")
	public String getBasVesselInfoId() {
		return basVesselInfoId;
	}

	/**
	 * Set Phisical
	 * Primary Key
	 */
	public void setBasVesselInfoId(String basVesselInfoId) {
		this.basVesselInfoId = basVesselInfoId;
		addValidField(FieldNames.basVesselInfoId);
	}

	/**
	 * Get 船舶代码
	 */
	@Column(name = "VESSEL_CODE")
	public String getVesselCode() {
		return vesselCode;
	}

	/**
	 * Set 船舶代码
	 */
	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
		addValidField(FieldNames.vesselCode);
	}

	/**
	 * Get 英文名称
	 */
	@Column(name = "VESSEL_NAME")
	public String getVesselName() {
		return vesselName;
	}

	/**
	 * Set 英文名称
	 */
	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
		addValidField(FieldNames.vesselName);
	}

	/**
	 * Get 中文名称
	 */
	@Column(name = "VESSEL_NAME_CN")
	public String getVesselNameCn() {
		return vesselNameCn;
	}

	/**
	 * Set 中文名称
	 */
	public void setVesselNameCn(String vesselNameCn) {
		this.vesselNameCn = vesselNameCn;
		addValidField(FieldNames.vesselNameCn);
	}

	/**
	 * Get 开始代理日期
	 */
	@Column(name = "AGENT_START_DATE")
	public Date getAgentStartDate() {
		return agentStartDate;
	}

	/**
	 * Set 开始代理日期
	 */
	public void setAgentStartDate(Date agentStartDate) {
		this.agentStartDate = agentStartDate;
		addValidField(FieldNames.agentStartDate);
	}

	/**
	 * Get 终止代理日期
	 */
	@Column(name = "AGENT_END_DATE")
	public Date getAgentEndDate() {
		return agentEndDate;
	}

	/**
	 * Set 终止代理日期
	 */
	public void setAgentEndDate(Date agentEndDate) {
		this.agentEndDate = agentEndDate;
		addValidField(FieldNames.agentEndDate);
	}

	/**
	 * Get 国家代码
	 */
	@Column(name = "COUNTRY")
	public String getCountry() {
		return country;
	}

	/**
	 * Set 国家代码
	 */
	public void setCountry(String country) {
		this.country = country;
		addValidField(FieldNames.country);
	}

	/**
	 * Get 净重吨
	 */
	@Column(name = "NET_TONS")
	public Double getNetTons() {
		return netTons;
	}

	/**
	 * Set 净重吨
	 */
	public void setNetTons(Double netTons) {
		this.netTons = netTons;
		addValidField(FieldNames.netTons);
	}

	/**
	 * Get 载重吨
	 */
	@Column(name = "DEADWEIGHT_TONS")
	public Double getDeadweightTons() {
		return deadweightTons;
	}

	/**
	 * Set 载重吨
	 */
	public void setDeadweightTons(Double deadweightTons) {
		this.deadweightTons = deadweightTons;
		addValidField(FieldNames.deadweightTons);
	}

	/**
	 * Get 船舶种类
	 */
	@Column(name = "VESSEL_CATEGORY")
	public String getVesselCategory() {
		return vesselCategory;
	}

	/**
	 * Set 船舶种类
	 */
	public void setVesselCategory(String vesselCategory) {
		this.vesselCategory = vesselCategory;
		addValidField(FieldNames.vesselCategory);
	}

	/**
	 * Get 船舶所属性质
	 */
	@Column(name = "VESSEL_OWNERSHIP")
	public String getVesselOwnership() {
		return vesselOwnership;
	}

	/**
	 * Set 船舶所属性质
	 */
	public void setVesselOwnership(String vesselOwnership) {
		this.vesselOwnership = vesselOwnership;
		addValidField(FieldNames.vesselOwnership);
	}

	/**
	 * Get 海关监管簿编号
	 */
	@Column(name = "CUSTOM_BOOK_NO")
	public String getCustomBookNo() {
		return customBookNo;
	}

	/**
	 * Set 海关监管簿编号
	 */
	public void setCustomBookNo(String customBookNo) {
		this.customBookNo = customBookNo;
		addValidField(FieldNames.customBookNo);
	}

	/**
	 * Get 海关监管簿船舶代码
	 */
	@Column(name = "CUSTOM_VESSEL_NO")
	public String getCustomVesselNo() {
		return customVesselNo;
	}

	/**
	 * Set 海关监管簿船舶代码
	 */
	public void setCustomVesselNo(String customVesselNo) {
		this.customVesselNo = customVesselNo;
		addValidField(FieldNames.customVesselNo);
	}

	/**
	 * Get 海关监管簿内河编码
	 */
	@Column(name = "CUSTOM_INLAND_NO")
	public String getCustomInlandNo() {
		return customInlandNo;
	}

	/**
	 * Set 海关监管簿内河编码
	 */
	public void setCustomInlandNo(String customInlandNo) {
		this.customInlandNo = customInlandNo;
		addValidField(FieldNames.customInlandNo);
	}

	/**
	 * Get 船东代码
	 */
	@Column(name = "VESSEL_OWNER_CODE")
	public String getVesselOwnerCode() {
		return vesselOwnerCode;
	}

	/**
	 * Set 船东代码
	 */
	public void setVesselOwnerCode(String vesselOwnerCode) {
		this.vesselOwnerCode = vesselOwnerCode;
		addValidField(FieldNames.vesselOwnerCode);
	}

	/**
	 * Get 船东公司名称
	 */
	@Column(name = "VESSEL_OWNER_NAME")
	public String getVesselOwnerName() {
		return vesselOwnerName;
	}

	/**
	 * Set 船东公司名称
	 */
	public void setVesselOwnerName(String vesselOwnerName) {
		this.vesselOwnerName = vesselOwnerName;
		addValidField(FieldNames.vesselOwnerName);
	}

	/**
	 * Get 船东公司地址
	 */
	@Column(name = "VESSEL_OWNER_ADDRESS")
	public String getVesselOwnerAddress() {
		return vesselOwnerAddress;
	}

	/**
	 * Set 船东公司地址
	 */
	public void setVesselOwnerAddress(String vesselOwnerAddress) {
		this.vesselOwnerAddress = vesselOwnerAddress;
		addValidField(FieldNames.vesselOwnerAddress);
	}

	/**
	 * Get 船东联系人
	 */
	@Column(name = "VESSEL_OWNER_CONTACT")
	public String getVesselOwnerContact() {
		return vesselOwnerContact;
	}

	/**
	 * Set 船东联系人
	 */
	public void setVesselOwnerContact(String vesselOwnerContact) {
		this.vesselOwnerContact = vesselOwnerContact;
		addValidField(FieldNames.vesselOwnerContact);
	}

	/**
	 * Get 船东联系人电话
	 */
	@Column(name = "VESSEL_OWNER_TEL")
	public String getVesselOwnerTel() {
		return vesselOwnerTel;
	}

	/**
	 * Set 船东联系人电话
	 */
	public void setVesselOwnerTel(String vesselOwnerTel) {
		this.vesselOwnerTel = vesselOwnerTel;
		addValidField(FieldNames.vesselOwnerTel);
	}

	/**
	 * Get 船上联系人
	 */
	@Column(name = "VESSEL_ONBOARD_CONTACT")
	public String getVesselOnboardContact() {
		return vesselOnboardContact;
	}

	/**
	 * Set 船上联系人
	 */
	public void setVesselOnboardContact(String vesselOnboardContact) {
		this.vesselOnboardContact = vesselOnboardContact;
		addValidField(FieldNames.vesselOnboardContact);
	}

	/**
	 * Get 船上联系人电话(HK)
	 */
	@Column(name = "VESSEL_ONBOARD_TEL_HK")
	public String getVesselOnboardTelHk() {
		return vesselOnboardTelHk;
	}

	/**
	 * Set 船上联系人电话(HK)
	 */
	public void setVesselOnboardTelHk(String vesselOnboardTelHk) {
		this.vesselOnboardTelHk = vesselOnboardTelHk;
		addValidField(FieldNames.vesselOnboardTelHk);
	}

	/**
	 * Get 船上联系人电话(国内)
	 */
	@Column(name = "VESSEL_ONBOARD_TEL_IL")
	public String getVesselOnboardTelIl() {
		return vesselOnboardTelIl;
	}

	/**
	 * Set 船上联系人电话(国内)
	 */
	public void setVesselOnboardTelIl(String vesselOnboardTelIl) {
		this.vesselOnboardTelIl = vesselOnboardTelIl;
		addValidField(FieldNames.vesselOnboardTelIl);
	}

	/**
	 * Get 船长名称
	 */
	@Column(name = "VESSEL_CAPTAIN")
	public String getVesselCaptain() {
		return vesselCaptain;
	}

	/**
	 * Set 船长名称
	 */
	public void setVesselCaptain(String vesselCaptain) {
		this.vesselCaptain = vesselCaptain;
		addValidField(FieldNames.vesselCaptain);
	}

	/**
	 * Get 香港海关编号
	 */
	@Column(name = "CUSTOM_CODE_HK")
	public String getCustomCodeHk() {
		return customCodeHk;
	}

	/**
	 * Set 香港海关编号
	 */
	public void setCustomCodeHk(String customCodeHk) {
		this.customCodeHk = customCodeHk;
		addValidField(FieldNames.customCodeHk);
	}

	/**
	 * Get 船籍港
	 */
	@Column(name = "PORT_ORIGIN_REGISTERED")
	public String getPortOriginRegistered() {
		return portOriginRegistered;
	}

	/**
	 * Set 船籍港
	 */
	public void setPortOriginRegistered(String portOriginRegistered) {
		this.portOriginRegistered = portOriginRegistered;
		addValidField(FieldNames.portOriginRegistered);
	}

	/**
	 * Get 船舶建造日期
	 */
	@Column(name = "DATE_MADE")
	public Date getDateMade() {
		return dateMade;
	}

	/**
	 * Set 船舶建造日期
	 */
	public void setDateMade(Date dateMade) {
		this.dateMade = dateMade;
		addValidField(FieldNames.dateMade);
	}

	/**
	 * Get 船长(M)
	 */
	@Column(name = "LENGTH")
	public Double getLength() {
		return length;
	}

	/**
	 * Set 船长(M)
	 */
	public void setLength(Double length) {
		this.length = length;
		addValidField(FieldNames.length);
	}

	/**
	 * Get 船宽(M)
	 */
	@Column(name = "WIDTH")
	public Double getWidth() {
		return width;
	}

	/**
	 * Set 船宽(M)
	 */
	public void setWidth(Double width) {
		this.width = width;
		addValidField(FieldNames.width);
	}

	/**
	 * Get 船高(M)
	 */
	@Column(name = "HEIGHT")
	public Double getHeight() {
		return height;
	}

	/**
	 * Set 船高(M)
	 */
	public void setHeight(Double height) {
		this.height = height;
		addValidField(FieldNames.height);
	}

	/**
	 * Get 主机数目
	 */
	@Column(name = "MOTOR_QTY")
	public Integer getMotorQty() {
		return motorQty;
	}

	/**
	 * Set 主机数目
	 */
	public void setMotorQty(Integer motorQty) {
		this.motorQty = motorQty;
		addValidField(FieldNames.motorQty);
	}

	/**
	 * Get 主机功率
	 */
	@Column(name = "MAIN_MOTOR_POWER")
	public Double getMainMotorPower() {
		return mainMotorPower;
	}

	/**
	 * Set 主机功率
	 */
	public void setMainMotorPower(Double mainMotorPower) {
		this.mainMotorPower = mainMotorPower;
		addValidField(FieldNames.mainMotorPower);
	}

	/**
	 * Get 主机耗油标准(每小时耗油KG)
	 */
	@Column(name = "MAIN_MOTOR_OIL")
	public Double getMainMotorOil() {
		return mainMotorOil;
	}

	/**
	 * Set 主机耗油标准(每小时耗油KG)
	 */
	public void setMainMotorOil(Double mainMotorOil) {
		this.mainMotorOil = mainMotorOil;
		addValidField(FieldNames.mainMotorOil);
	}

	/**
	 * Get 副机功率
	 */
	@Column(name = "BACKUP_MOTOR_POWER")
	public Double getBackupMotorPower() {
		return backupMotorPower;
	}

	/**
	 * Set 副机功率
	 */
	public void setBackupMotorPower(Double backupMotorPower) {
		this.backupMotorPower = backupMotorPower;
		addValidField(FieldNames.backupMotorPower);
	}

	/**
	 * Get 副机耗油标准(每小时KG)
	 */
	@Column(name = "BACKUP_MOTOR_OIL")
	public Double getBackupMotorOil() {
		return backupMotorOil;
	}

	/**
	 * Set 副机耗油标准(每小时KG)
	 */
	public void setBackupMotorOil(Double backupMotorOil) {
		this.backupMotorOil = backupMotorOil;
		addValidField(FieldNames.backupMotorOil);
	}

	/**
	 * Get 担数（船舶总功率的指标数）
	 */
	@Column(name = "VESSEL_POWER_TOTAL")
	public Double getVesselPowerTotal() {
		return vesselPowerTotal;
	}

	/**
	 * Set 担数（船舶总功率的指标数）
	 */
	public void setVesselPowerTotal(Double vesselPowerTotal) {
		this.vesselPowerTotal = vesselPowerTotal;
		addValidField(FieldNames.vesselPowerTotal);
	}

	/**
	 * Get 总吨
	 */
	@Column(name = "TON_TOTAL")
	public Double getTonTotal() {
		return tonTotal;
	}

	/**
	 * Set 总吨
	 */
	public void setTonTotal(Double tonTotal) {
		this.tonTotal = tonTotal;
		addValidField(FieldNames.tonTotal);
	}

	/**
	 * Get 净吨
	 */
	@Column(name = "TON_NET")
	public Double getTonNet() {
		return tonNet;
	}

	/**
	 * Set 净吨
	 */
	public void setTonNet(Double tonNet) {
		this.tonNet = tonNet;
		addValidField(FieldNames.tonNet);
	}

	/**
	 * Get 载重吨
	 */
	@Column(name = "TON_LOAD")
	public Double getTonLoad() {
		return tonLoad;
	}

	/**
	 * Set 载重吨
	 */
	public void setTonLoad(Double tonLoad) {
		this.tonLoad = tonLoad;
		addValidField(FieldNames.tonLoad);
	}

	/**
	 * Get 总TEU
	 */
	@Column(name = "TEU_TOTAL")
	public Integer getTeuTotal() {
		return teuTotal;
	}

	/**
	 * Set 总TEU
	 */
	public void setTeuTotal(Integer teuTotal) {
		this.teuTotal = teuTotal;
		addValidField(FieldNames.teuTotal);
	}

	/**
	 * Get 吉TEU
	 */
	@Column(name = "TEU_EMPTY")
	public Integer getTeuEmpty() {
		return teuEmpty;
	}

	/**
	 * Set 吉TEU
	 */
	public void setTeuEmpty(Integer teuEmpty) {
		this.teuEmpty = teuEmpty;
		addValidField(FieldNames.teuEmpty);
	}

	/**
	 * Get 重TEU
	 */
	@Column(name = "TEU_FULL")
	public Double getTeuFull() {
		return teuFull;
	}

	/**
	 * Set 重TEU
	 */
	public void setTeuFull(Double teuFull) {
		this.teuFull = teuFull;
		addValidField(FieldNames.teuFull);
	}

	/**
	 * Get CMO编号
	 */
	@Column(name = "CMO")
	public String getCmo() {
		return cmo;
	}

	/**
	 * Set CMO编号
	 */
	public void setCmo(String cmo) {
		this.cmo = cmo;
		addValidField(FieldNames.cmo);
	}

	/**
	 * Get 吃水深度
	 */
	@Column(name = "WATER_BELOW")
	public Double getWaterBelow() {
		return waterBelow;
	}

	/**
	 * Set 吃水深度
	 */
	public void setWaterBelow(Double waterBelow) {
		this.waterBelow = waterBelow;
		addValidField(FieldNames.waterBelow);
	}

	/**
	 * Get 操作及适航
	 */
	@Column(name = "CONDITION")
	public String getCondition() {
		return condition;
	}

	/**
	 * Set 操作及适航
	 */
	public void setCondition(String condition) {
		this.condition = condition;
		addValidField(FieldNames.condition);
	}

	/**
	 * Get 特殊情况
	 */
	@Column(name = "SPECIAL")
	public String getSpecial() {
		return special;
	}

	/**
	 * Set 特殊情况
	 */
	public void setSpecial(String special) {
		this.special = special;
		addValidField(FieldNames.special);
	}

	/**
	 * Get 船上所载危险品
	 */
	@Column(name = "DANGER")
	public String getDanger() {
		return danger;
	}

	/**
	 * Set 船上所载危险品
	 */
	public void setDanger(String danger) {
		this.danger = danger;
		addValidField(FieldNames.danger);
	}

	/**
	 * Get 香港停靠码头
	 */
	@Column(name = "PIER_HK")
	public String getPierHk() {
		return pierHk;
	}

	/**
	 * Set 香港停靠码头
	 */
	public void setPierHk(String pierHk) {
		this.pierHk = pierHk;
		addValidField(FieldNames.pierHk);
	}

	/**
	 * Get 香港牌照号码
	 */
	@Column(name = "HK_LICENSE")
	public String getHkLicense() {
		return hkLicense;
	}

	/**
	 * Set 香港牌照号码
	 */
	public void setHkLicense(String hkLicense) {
		this.hkLicense = hkLicense;
		addValidField(FieldNames.hkLicense);
	}

	/**
	 * Get 外地注册号码
	 */
	@Column(name = "OFFICIAL_NO")
	public String getOfficialNo() {
		return officialNo;
	}

	/**
	 * Set 外地注册号码
	 */
	public void setOfficialNo(String officialNo) {
		this.officialNo = officialNo;
		addValidField(FieldNames.officialNo);
	}

	/**
	 * Get 边防备案号
	 */
	@Column(name = "BORDER_RECORD")
	public String getBorderRecord() {
		return borderRecord;
	}

	/**
	 * Set 边防备案号
	 */
	public void setBorderRecord(String borderRecord) {
		this.borderRecord = borderRecord;
		addValidField(FieldNames.borderRecord);
	}

	/**
	 * Get 船舶证书
	 */
	@Column(name = "SHIP_CERTIFICATE")
	public String getShipCertificate() {
		return shipCertificate;
	}

	/**
	 * Set 船舶证书
	 */
	public void setShipCertificate(String shipCertificate) {
		this.shipCertificate = shipCertificate;
		addValidField(FieldNames.shipCertificate);
	}

	/**
	 * Get 海关备案号
	 */
	@Column(name = "CUSTOMS_RECORD")
	public String getCustomsRecord() {
		return customsRecord;
	}

	/**
	 * Set 海关备案号
	 */
	public void setCustomsRecord(String customsRecord) {
		this.customsRecord = customsRecord;
		addValidField(FieldNames.customsRecord);
	}

	/**
	 * Get 控制字
	 */
	@Column(name = "CONTROL_WORD")
	public String getControlWord() {
		return controlWord;
	}

	/**
	 * Set 控制字
	 */
	public void setControlWord(String controlWord) {
		this.controlWord = controlWord;
		addValidField(FieldNames.controlWord);
	}

	/**
	 * Get 备注
	 */
	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	/**
	 * Set 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
		addValidField(FieldNames.remark);
	}

	/**
	 * Get 有效
	 */
	@Column(name = "ACTIVE")
	public Integer getActive() {
		return active;
	}

	/**
	 * Set 有效
	 */
	public void setActive(Integer active) {
		this.active = active;
		addValidField(FieldNames.active);
	}

	/**
	 * Get 航线种类ID
	 */
	@Column(name = "BAS_LINE_CATEGORY_ID")
	public String getBasLineCategoryId() {
		return basLineCategoryId;
	}

	/**
	 * Set 航线种类ID
	 */
	public void setBasLineCategoryId(String basLineCategoryId) {
		this.basLineCategoryId = basLineCategoryId;
		addValidField(FieldNames.basLineCategoryId);
	}

	/**
	 * Get 船检登记号
	 */
	@Column(name = "SHIP_REGISTER_NO")
	public String getShipRegisterNo() {
		return shipRegisterNo;
	}

	/**
	 * Set 船检登记号
	 */
	public void setShipRegisterNo(String shipRegisterNo) {
		this.shipRegisterNo = shipRegisterNo;
		addValidField(FieldNames.shipRegisterNo);
	}

	/**
	 * Get 船舶编号
	 */
	@Column(name = "SHIP_NO")
	public String getShipNo() {
		return shipNo;
	}

	/**
	 * Set 船舶编号
	 */
	public void setShipNo(String shipNo) {
		this.shipNo = shipNo;
		addValidField(FieldNames.shipNo);
	}

	/**
	 * Get HIT
	 * ID
	 */
	@Column(name = "HIT_ID")
	public String getHitId() {
		return hitId;
	}

	/**
	 * Set HIT
	 * ID
	 */
	public void setHitId(String hitId) {
		this.hitId = hitId;
		addValidField(FieldNames.hitId);
	}

	/**
	 * Get RTT
	 * ID
	 */
	@Column(name = "RTT_ID")
	public String getRttId() {
		return rttId;
	}

	/**
	 * Set RTT
	 * ID
	 */
	public void setRttId(String rttId) {
		this.rttId = rttId;
		addValidField(FieldNames.rttId);
	}

	/**
	 * Get 重40
	 */
	@Column(name = "FULL40")
	public Long getFull40() {
		return full40;
	}

	/**
	 * Set 重40
	 */
	public void setFull40(Long full40) {
		this.full40 = full40;
		addValidField(FieldNames.full40);
	}

	/**
	 * Get 吉40
	 */
	@Column(name = "EMPTY40")
	public Long getEmpty40() {
		return empty40;
	}

	/**
	 * Set 吉40
	 */
	public void setEmpty40(Long empty40) {
		this.empty40 = empty40;
		addValidField(FieldNames.empty40);
	}

	/**
	 * Get 吉2TEU
	 */
	@Column(name = "TEU_EMPTY2")
	public Integer getTeuEmpty2() {
		return teuEmpty2;
	}

	/**
	 * Set 吉2TEU
	 */
	public void setTeuEmpty2(Integer teuEmpty2) {
		this.teuEmpty2 = teuEmpty2;
		addValidField(FieldNames.teuEmpty2);
	}

	/**
	 * Get 重2TEU
	 */
	@Column(name = "TEU_FULL2")
	public Double getTeuFull2() {
		return teuFull2;
	}

	/**
	 * Set 重2TEU
	 */
	public void setTeuFull2(Double teuFull2) {
		this.teuFull2 = teuFull2;
		addValidField(FieldNames.teuFull2);
	}

	/**
	 * Get 租船公司
	 */
	@Column(name = "RENT_VESSEL_COMPANY")
	public String getRentVesselCompany() {
		return rentVesselCompany;
	}

	/**
	 * Set 租船公司
	 */
	public void setRentVesselCompany(String rentVesselCompany) {
		this.rentVesselCompany = rentVesselCompany;
		addValidField(FieldNames.rentVesselCompany);
	}

	/**
	 * Get 航线类别
	 */
	@Column(name = "ROUTE_CATEGORY")
	public String getRouteCategory() {
		return routeCategory;
	}

	/**
	 * Set 航线类别
	 */
	public void setRouteCategory(String routeCategory) {
		this.routeCategory = routeCategory;
		addValidField(FieldNames.routeCategory);
	}

	/**
	 * Get 配载技巧
	 */
	@Column(name = "STOWAGE_SKILL")
	public String getStowageSkill() {
		return stowageSkill;
	}

	/**
	 * Set 配载技巧
	 */
	public void setStowageSkill(String stowageSkill) {
		this.stowageSkill = stowageSkill;
		addValidField(FieldNames.stowageSkill);
	}

	/**
	 * Get 进港证费
	 */
	@Column(name = "IN_PORT_BILL")
	public Double getInPortBill() {
		return inPortBill;
	}

	/**
	 * Set 进港证费
	 */
	public void setInPortBill(Double inPortBill) {
		this.inPortBill = inPortBill;
		addValidField(FieldNames.inPortBill);
	}

	/**
	 * Get parkPortBill
	 */
	@Column(name = "PARK_PORT_BILL")
	public Double getParkPortBill() {
		return parkPortBill;
	}

	/**
	 * Set parkPortBill
	 */
	public void setParkPortBill(Double parkPortBill) {
		this.parkPortBill = parkPortBill;
		addValidField(FieldNames.parkPortBill);
	}

	/**
	 * Get dragShipBill
	 */
	@Column(name = "DRAG_SHIP_BILL")
	public Double getDragShipBill() {
		return dragShipBill;
	}

	/**
	 * Set dragShipBill
	 */
	public void setDragShipBill(Double dragShipBill) {
		this.dragShipBill = dragShipBill;
		addValidField(FieldNames.dragShipBill);
	}

	/**
	 * Get outPortBill
	 */
	@Column(name = "OUT_PORT_BILL")
	public Double getOutPortBill() {
		return outPortBill;
	}

	/**
	 * Set outPortBill
	 */
	public void setOutPortBill(Double outPortBill) {
		this.outPortBill = outPortBill;
		addValidField(FieldNames.outPortBill);
	}

	/**
	 * Get proxyBill
	 */
	@Column(name = "PROXY_BILL")
	public Double getProxyBill() {
		return proxyBill;
	}

	/**
	 * Set proxyBill
	 */
	public void setProxyBill(Double proxyBill) {
		this.proxyBill = proxyBill;
		addValidField(FieldNames.proxyBill);
	}

	/**
	 * Get multiPassBill
	 */
	@Column(name = "MULTI_PASS_BILL")
	public Double getMultiPassBill() {
		return multiPassBill;
	}

	/**
	 * Set multiPassBill
	 */
	public void setMultiPassBill(Double multiPassBill) {
		this.multiPassBill = multiPassBill;
		addValidField(FieldNames.multiPassBill);
	}

	/**
	 * Get 内河编码
	 */
	@Column(name = "RIVER_CODE")
	public String getRiverCode() {
		return riverCode;
	}

	/**
	 * Set 内河编码
	 */
	public void setRiverCode(String riverCode) {
		this.riverCode = riverCode;
		addValidField(FieldNames.riverCode);
	}

	/**
	 * Get 税品编号
	 */
	@Column(name = "TAX_NO")
	public String getTaxNo() {
		return taxNo;
	}

	/**
	 * Set 税品编号
	 */
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
		addValidField(FieldNames.taxNo);
	}

	/**
	 * Get 船舶所属关区(中文)
	 */
	@Column(name = "SHIP_BELONG_CUSTOMAREA")
	public String getShipBelongCustomarea() {
		return shipBelongCustomarea;
	}

	/**
	 * Set 船舶所属关区(中文)
	 */
	public void setShipBelongCustomarea(String shipBelongCustomarea) {
		this.shipBelongCustomarea = shipBelongCustomarea;
		addValidField(FieldNames.shipBelongCustomarea);
	}

	/**
	 * Get 启用时间(危险品)
	 */
	@Column(name = "ENABLE_DATE")
	public Date getEnableDate() {
		return enableDate;
	}

	/**
	 * Set 启用时间(危险品)
	 */
	public void setEnableDate(Date enableDate) {
		this.enableDate = enableDate;
		addValidField(FieldNames.enableDate);
	}

	/**
	 * Get 终止时间(危险品)
	 */
	@Column(name = "TERMINAT_DATE")
	public Date getTerminatDate() {
		return terminatDate;
	}

	/**
	 * Set 终止时间(危险品)
	 */
	public void setTerminatDate(Date terminatDate) {
		this.terminatDate = terminatDate;
		addValidField(FieldNames.terminatDate);
	}

	/**
	 * Get 开始代理日期
	 */
	@Column(name = "DATE_AGENT_START")
	public Date getDateAgentStart() {
		return dateAgentStart;
	}

	/**
	 * Set 开始代理日期
	 */
	public void setDateAgentStart(Date dateAgentStart) {
		this.dateAgentStart = dateAgentStart;
		addValidField(FieldNames.dateAgentStart);
	}

	/**
	 * Get 结束代理日期
	 */
	@Column(name = "DATE_AGENT_END")
	public Date getDateAgentEnd() {
		return dateAgentEnd;
	}

	/**
	 * Set 结束代理日期
	 */
	public void setDateAgentEnd(Date dateAgentEnd) {
		this.dateAgentEnd = dateAgentEnd;
		addValidField(FieldNames.dateAgentEnd);
	}

	/**
	 * Get 录入人
	 */
	@Column(name = "CREATED_BY_USER")
	public String getCreatedByUser() {
		return createdByUser;
	}

	/**
	 * Set 录入人
	 */
	public void setCreatedByUser(String createdByUser) {
		this.createdByUser = createdByUser;
		addValidField(FieldNames.createdByUser);
	}

	/**
	 * Get 创建组织
	 */
	@Column(name = "CREATE_OFFICE")
	public String getCreateOffice() {
		return createOffice;
	}

	/**
	 * Set 创建组织
	 */
	public void setCreateOffice(String createOffice) {
		this.createOffice = createOffice;
		addValidField(FieldNames.createOffice);
	}

	/**
	 * Get 创建时间
	 */
	@Column(name = "CREATED_DTM_LOC")
	public Date getCreatedDtmLoc() {
		return createdDtmLoc;
	}

	/**
	 * Set 创建时间
	 */
	public void setCreatedDtmLoc(Date createdDtmLoc) {
		this.createdDtmLoc = createdDtmLoc;
		addValidField(FieldNames.createdDtmLoc);
	}

	/**
	 * Get 创建者当地时区
	 */
	@Column(name = "CREATED_TIME_ZONE")
	public String getCreatedTimeZone() {
		return createdTimeZone;
	}

	/**
	 * Set 创建者当地时区
	 */
	public void setCreatedTimeZone(String createdTimeZone) {
		this.createdTimeZone = createdTimeZone;
		addValidField(FieldNames.createdTimeZone);
	}

	/**
	 * Get 修改人
	 */
	@Column(name = "UPDATED_BY_USER")
	public String getUpdatedByUser() {
		return updatedByUser;
	}

	/**
	 * Set 修改人
	 */
	public void setUpdatedByUser(String updatedByUser) {
		this.updatedByUser = updatedByUser;
		addValidField(FieldNames.updatedByUser);
	}

	/**
	 * Get 修改组织
	 */
	@Column(name = "UPDATED_OFFICE")
	public String getUpdatedOffice() {
		return updatedOffice;
	}

	/**
	 * Set 修改组织
	 */
	public void setUpdatedOffice(String updatedOffice) {
		this.updatedOffice = updatedOffice;
		addValidField(FieldNames.updatedOffice);
	}

	/**
	 * Get 修改时间
	 */
	@Column(name = "UPDATED_DTM_LOC")
	public Date getUpdatedDtmLoc() {
		return updatedDtmLoc;
	}

	/**
	 * Set 修改时间
	 */
	public void setUpdatedDtmLoc(Date updatedDtmLoc) {
		this.updatedDtmLoc = updatedDtmLoc;
		addValidField(FieldNames.updatedDtmLoc);
	}

	/**
	 * Get 修改者当地时区
	 */
	@Column(name = "UPDATED_TIME_ZONE")
	public String getUpdatedTimeZone() {
		return updatedTimeZone;
	}

	/**
	 * Set 修改者当地时区
	 */
	public void setUpdatedTimeZone(String updatedTimeZone) {
		this.updatedTimeZone = updatedTimeZone;
		addValidField(FieldNames.updatedTimeZone);
	}

	/**
	 * Get 记录版本
	 */
	@Column(name = "RECORD_VERSION")
	@Version
	public Long getRecordVersion() {
		return recordVersion;
	}

	/**
	 * Set 记录版本
	 */
	public void setRecordVersion(Long recordVersion) {
		this.recordVersion = recordVersion;
		addValidField(FieldNames.recordVersion);
	}

	/**
	 * Get 公司代码
	 */
	@Column(name = "PRINCIPAL_GROUP_CODE")
	public String getPrincipalGroupCode() {
		return principalGroupCode;
	}

	/**
	 * Set 公司代码
	 */
	public void setPrincipalGroupCode(String principalGroupCode) {
		this.principalGroupCode = principalGroupCode;
		addValidField(FieldNames.principalGroupCode);
	}

	/**
	 * Get 冻插数
	 */
	@Column(name = "FREEZE_PLUG_NUMBER")
	public Long getFreezePlugNumber() {
		return freezePlugNumber;
	}

	/**
	 * Set 冻插数
	 */
	public void setFreezePlugNumber(Long freezePlugNumber) {
		this.freezePlugNumber = freezePlugNumber;
		addValidField(FieldNames.freezePlugNumber);
	}

}
