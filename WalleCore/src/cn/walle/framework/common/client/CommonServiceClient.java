package cn.walle.framework.common.client;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.walle.framework.httpclient.WalleHttpClient;

public class CommonServiceClient extends WalleHttpClient {

	private String commonQueryService = "commonQueryManager";
	private String commonQueryMethod = "query";
	private String commonSaveService = "commonSaveManager";
	private String commonSaveMethod = "saveAll";

	public JSONObject query(JSONObject queryInfo) {
		try {
			JSONObject parameters = new JSONObject();
			parameters.put("queryInfo", queryInfo);
			JSONObject result = super.requestJsonFacade(commonQueryService, commonQueryMethod, parameters);
			return result.getJSONObject("result");
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public JSONArray saveAll(JSONArray models) {
		try {
			JSONObject parameters = new JSONObject();
			parameters.put("models", models);
			JSONObject result = super.requestJsonFacade(commonSaveService, commonSaveMethod, parameters);
			return result.getJSONArray("result");
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	public String getCommonQueryService() {
		return commonQueryService;
	}
	public void setCommonQueryService(String commonQueryService) {
		this.commonQueryService = commonQueryService;
	}
	public String getCommonQueryMethod() {
		return commonQueryMethod;
	}
	public void setCommonQueryMethod(String commonQueryMethod) {
		this.commonQueryMethod = commonQueryMethod;
	}
	public String getCommonSaveService() {
		return commonSaveService;
	}
	public void setCommonSaveService(String commonSaveService) {
		this.commonSaveService = commonSaveService;
	}
	public String getCommonSaveMethod() {
		return commonSaveMethod;
	}
	public void setCommonSaveMethod(String commonSaveMethod) {
		this.commonSaveMethod = commonSaveMethod;
	}
	
}
