package cn.walle.framework.core.support.hibernate;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.hibernate.transform.ResultTransformer;

import cn.walle.framework.core.model.DynamicModelClass;
import cn.walle.framework.core.util.SqlUtils;

public class DynamicModelClassResultTransformer implements ResultTransformer {

	private static DynamicModelClassResultTransformer instance = new DynamicModelClassResultTransformer();
	
	public static DynamicModelClassResultTransformer getInstance() {
		return instance;
	}
	
	@Override
	public List transformList(List collection) {
		return collection;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		DynamicModelClass result = new DynamicModelClass();
		for (int i = 0; i < aliases.length; i++) {
			String aliase = aliases[i];
			if ("UUID__".equals(aliase)) {
				if (ArrayUtils.contains(aliases, "uuid")) {
					aliase = "uuid__";
				} else {
					aliase = "uuid";
				}
			} else {
				aliase = SqlUtils.dbNameToJavaName(aliase, false);
			}
			result.put(aliase, tuple[i]);
		}
		return result;
	}

}
