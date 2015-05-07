package cn.walle.framework.tools.testclient;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import cn.walle.framework.core.util.ReflectionUtils;
import cn.walle.framework.tools.base.treemenu.TreeNode;
import cn.walle.framework.tools.base.treemenu.TreeNodeLoader;

public class MethodNodeLoader implements TreeNodeLoader {

	public void loadChildren(TreeNode treeNode) throws Exception {
		String interfaceName = treeNode.getName();
		String packageName = ((TreeNode) treeNode.getParent()).getName();
		Class<?> interfaceClass = Class.forName(packageName + "." + interfaceName);
		
		Class<?> implClass = Class.forName(packageName + ".impl." + interfaceName + "Impl");
		Map<Method, String[]> implMethodsParameterNames = ReflectionUtils.getMethodsParameterNames(implClass);
		Map<String, String[]> implMethodSignaturesParameterNames = new LinkedHashMap<String, String[]>();
		for (Method method : implMethodsParameterNames.keySet()) {
			String methodSignature = ReflectionUtils.getMethodSignature(method);
			String[] parameterNames = implMethodsParameterNames.get(method);
			if (! implMethodSignaturesParameterNames.containsKey(methodSignature)) {
				implMethodSignaturesParameterNames.put(methodSignature, parameterNames);
			}
		}
		
		Method[] interfaceMethods = interfaceClass.getMethods();
		Map<String, Method> interfaceMethodSignatures = new HashMap<String, Method>();
		for (Method method : interfaceMethods) {
			interfaceMethodSignatures.put(ReflectionUtils.getMethodSignature(method), method);
		}
		
		treeNode.removeAllChildren();

		for (String methodSignature : implMethodSignaturesParameterNames.keySet()) {
			if (interfaceMethodSignatures.containsKey(methodSignature)) {
				Method method = interfaceMethodSignatures.get(methodSignature);
				String[] parameterNames = implMethodSignaturesParameterNames.get(methodSignature);
				if (parameterNames == null) {
					parameterNames = new String[method.getParameterTypes().length];
					for (int i = 0; i < parameterNames.length; i++) {
						parameterNames[i] = "arg" + i;
					}
				}
				String methodNodeName = method.getName() + 
						"(" + StringUtils.arrayToDelimitedString(parameterNames, ", ") + ")";
				TreeNode methodNode = new TreeNode(
						methodNodeName, null, TestClientPanel.class, interfaceClass, method, parameterNames);
				treeNode.add(methodNode);
			}
		}
		
	}

}
