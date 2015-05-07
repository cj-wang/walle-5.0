package cn.walle.framework.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.StringUtils;

import cn.walle.framework.core.support.SystemConfig;

public class ClassUtils {

	private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
	private static MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
	
	public static List<Class<?>> getModelClasses() throws Exception {
		List<Class<?>> result = new ArrayList<Class<?>>();
		String[] basePackages = StringUtils.commaDelimitedListToStringArray(SystemConfig.BASE_PACKAGE);
		for (String basePackage : basePackages) {
			String searchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/model/*Model.class";
			Resource[] resources = resourcePatternResolver.getResources(searchPath);
			for (Resource resource : resources) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				String className = metadataReader.getClassMetadata().getClassName();
				Class<?> clazz = Class.forName(className);
				result.add(clazz);
			}
		}
		return result;
	}
	
	public static List<Class<?>> getQueryItemClasses() throws Exception {
		List<Class<?>> result = new ArrayList<Class<?>>();
		String[] basePackages = StringUtils.commaDelimitedListToStringArray(SystemConfig.BASE_PACKAGE);
		for (String basePackage : basePackages) {
			String searchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/query/**/*QueryItem.class";
			Resource[] resources = resourcePatternResolver.getResources(searchPath);
			for (Resource resource : resources) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				String className = metadataReader.getClassMetadata().getClassName();
				Class<?> clazz = Class.forName(className);
				result.add(clazz);
			}
		}
		return result;
	}
	
	public static List<Class<?>> getQueryConditionClasses() throws Exception {
		List<Class<?>> result = new ArrayList<Class<?>>();
		String[] basePackages = StringUtils.commaDelimitedListToStringArray(SystemConfig.BASE_PACKAGE);
		for (String basePackage : basePackages) {
			String searchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/query/**/*QueryCondition.class";
			Resource[] resources = resourcePatternResolver.getResources(searchPath);
			for (Resource resource : resources) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				String className = metadataReader.getClassMetadata().getClassName();
				Class<?> clazz = Class.forName(className);
				result.add(clazz);
			}
		}
		return result;
	}
	
	public static List<File> getFieldDefinitionXmlFiles() throws Exception {
		List<File> result = new ArrayList<File>();
		String[] basePackages = StringUtils.commaDelimitedListToStringArray(SystemConfig.BASE_PACKAGE);
		for (String basePackage : basePackages) {
			String searchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/model/*Fields.xml";
			Resource[] resources = resourcePatternResolver.getResources(searchPath);
			for (Resource resource : resources) {
				try {
					result.add(resource.getFile());
				} catch (FileNotFoundException fnfe) {
				}
			}
		}
		return result;
	}
	
	public static List<Resource> getHibernateMappingLocations() throws Exception {
		List<Resource> result = new ArrayList<Resource>();
		String[] basePackages = StringUtils.commaDelimitedListToStringArray(SystemConfig.BASE_PACKAGE);
		for (String basePackage : basePackages) {
			String searchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
					org.springframework.util.ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.hbm.xml";
			Resource[] resources = resourcePatternResolver.getResources(searchPath);
			result.addAll(Arrays.asList(resources));
		}
		return result;
	}
	

	
	public static TreeSet<String> getProjectPackageNames() {
		SessionFactory sessionFactory = ContextUtils.getBeanOfType(SessionFactory.class);
		Map<String, ClassMetadata> map = sessionFactory.getAllClassMetadata();
		TreeSet<String> packageNames = new TreeSet<String>();
		for (ClassMetadata classMetadata : map.values()) {
			String packageName = classMetadata.getMappedClass().getPackage().getName();
			if (packageName.endsWith(".model")) {
				packageNames.add(packageName.substring(0, packageName.length() - 6));
			}
		}
		return packageNames;
	}

}
