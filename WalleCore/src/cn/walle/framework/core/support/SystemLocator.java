package cn.walle.framework.core.support;

public interface SystemLocator {
	
	public static final String SYSTEM_VERSION_DEV = "DEV";
	public static final String SYSTEM_VERSION_SIT = "SIT";
	public static final String SYSTEM_VERSION_UAT = "UAT";
	public static final String SYSTEM_VERSION_PLT = "PLT";
	public static final String SYSTEM_VERSION_PRD = "PRD";
	
	public static final String NETWORK_INTRANET = "INTRANET";
	public static final String NETWORK_INTERNET = "INTERNET";

	/**
	 * Get a system location, of the same version and network with current system
	 * @param systemName
	 * @return
	 */
	String getSystemLocation(String systemName);
	
	/**
	 * Get a system location, of the specific version and network
	 * @param systemName
	 * @param systemVersion  if null, default to the same version with current system
	 * @param network  if null, default to the same network with current system
	 * @return
	 */
	String getSystemLocation(String systemName, String systemVersion, String network);
	
}
