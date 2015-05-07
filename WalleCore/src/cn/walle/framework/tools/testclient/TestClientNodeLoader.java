package cn.walle.framework.tools.testclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import cn.walle.framework.core.service.BaseManager;
import cn.walle.framework.core.util.ContextUtils;
import cn.walle.framework.tools.base.treemenu.TreeNode;
import cn.walle.framework.tools.base.treemenu.TreeNodeLoader;

public class TestClientNodeLoader implements TreeNodeLoader {
	
	private TreeNodeLoader methodNodeLoader = new MethodNodeLoader();

	public void loadChildren(TreeNode treeNode) throws Exception {
		Map<String, BaseManager> managers = ContextUtils.getBeansOfType(BaseManager.class);
		List<Class<? extends BaseManager>> managerInterfaces = new ArrayList<Class<? extends BaseManager>>();
		for (BaseManager manager : managers.values()) {
			Class<? extends BaseManager> managerClass = manager.getClass();
			Class<?>[] interfaces = managerClass.getInterfaces();
			for (Class<?> interfaceClass : interfaces) {
				if (BaseManager.class.isAssignableFrom(interfaceClass)
						&& BaseManager.class != interfaceClass) {
					managerInterfaces.add(interfaceClass.asSubclass(BaseManager.class));
				}
			}
		}
		
		TreeMap<String, TreeSet<String>> packageInterfacesMap = new TreeMap<String, TreeSet<String>>();
		for (Class<? extends BaseManager> managerInterface : managerInterfaces) {
			String packageName = managerInterface.getPackage().getName();
			if (packageInterfacesMap.containsKey(packageName)) {
				packageInterfacesMap.get(packageName).add(managerInterface.getSimpleName());
			} else {
				TreeSet<String> interfaceNames = new TreeSet<String>();
				interfaceNames.add(managerInterface.getSimpleName());
				packageInterfacesMap.put(packageName, interfaceNames);
			}
		}

		treeNode.removeAllChildren();
		for (String packageName : packageInterfacesMap.keySet()) {
			TreeNode packageNode = new TreeNode(packageName, null, null);
			for (String interfaceName : packageInterfacesMap.get(packageName)) {
				TreeNode classNode = new TreeNode(interfaceName, methodNodeLoader, null);
				packageNode.add(classNode);
			}
			treeNode.add(packageNode);
		}
	}

}
