package cn.walle.framework.core.util;

import java.io.InputStream;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

/**
 * Reflection utility methods.
 * @author cj
 *
 */
public class ReflectionUtils {
	
	private static Log log = LogFactory.getLog(ReflectionUtils.class);

	private static Map<Class<?>, Map<Method, String[]>> declaredMethodsParameterNamesCache =
		new HashMap<Class<?>, Map<Method,String[]>>();
	
	private static Map<Method, String[]> doGetDeclaredMethodsParameterNames(final Class<?> clazz) {
		if (log.isDebugEnabled()) {
			log.debug("getDeclaredMethodsParameterNames " + clazz);
		}
		final Map<Method, String[]> result = new LinkedHashMap<Method, String[]>();
		
		ClassReader classReader;
		try {
			String classResourceName = "/" + clazz.getName().replace('.', '/') + ".class";
			InputStream classResourceInputStream = ReflectionUtils.class.getResourceAsStream(classResourceName);
			classReader = new ClassReader(classResourceInputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		ClassVisitor classVisitor = new ClassVisitor() {
			
			private Method method;
			private String[] parameterNames;
			private int parameterIndex;

			@Override
			public void visit(int version, int access, String name,
					String signature, String superName, String[] interfaces) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitSource(String source, String debug) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitOuterClass(String owner, String name, String desc) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public AnnotationVisitor visitAnnotation(String desc,
					boolean visible) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void visitAttribute(Attribute attr) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitInnerClass(String name, String outerName,
					String innerName, int access) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public FieldVisitor visitField(int access, String name,
					String desc, String signature, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				if ("<init>".equals(name) || "<clinit>".equals(name)) {
					return null;
				}
				try {
					org.objectweb.asm.Type[] types = org.objectweb.asm.Type.getArgumentTypes(desc);
					Class<?>[] parameterTypes = new Class<?>[types.length];
					for (int i = 0; i < types.length; i++) {
						parameterTypes[i] = org.springframework.util.ClassUtils.forName(types[i].getClassName(), null);
					}
					
					method = clazz.getDeclaredMethod(name, parameterTypes);
					parameterNames = new String[parameterTypes.length];
					parameterIndex = 0;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
				
				return new MethodVisitor() {

					@Override
					public AnnotationVisitor visitAnnotationDefault() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public AnnotationVisitor visitAnnotation(String desc,
							boolean visible) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public AnnotationVisitor visitParameterAnnotation(
							int parameter, String desc, boolean visible) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public void visitAttribute(Attribute attr) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitCode() {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitFrame(int type, int nLocal,
							Object[] local, int nStack, Object[] stack) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitInsn(int opcode) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitIntInsn(int opcode, int operand) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitVarInsn(int opcode, int var) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitTypeInsn(int opcode, String type) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitFieldInsn(int opcode, String owner,
							String name, String desc) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitMethodInsn(int opcode, String owner,
							String name, String desc) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitJumpInsn(int opcode, Label label) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitLabel(Label label) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitLdcInsn(Object cst) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitIincInsn(int var, int increment) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitTableSwitchInsn(int min, int max,
							Label dflt, Label[] labels) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitLookupSwitchInsn(Label dflt, int[] keys,
							Label[] labels) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitMultiANewArrayInsn(String desc, int dims) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitTryCatchBlock(Label start, Label end,
							Label handler, String type) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitLocalVariable(String name, String desc,
							String signature, Label start, Label end, int index) {
						if ("this".equals(name)) {
							return;
						}
						if (parameterIndex < parameterNames.length) {
							parameterNames[parameterIndex] = name;
							parameterIndex++;
						}
					}

					@Override
					public void visitLineNumber(int line, Label start) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void visitMaxs(int maxStack, int maxLocals) {
						if (result.containsKey(method)) {
							return;
						}
						for (int i = 0; i < parameterNames.length; i++) {
							if (parameterNames[i] == null) {
								parameterNames[i] = "arg" + i;
							}
						}
						result.put(method, parameterNames);
					}

					@Override
					public void visitEnd() {
						// TODO Auto-generated method stub
						
					}
					
				};
			}

			@Override
			public void visitEnd() {
				// TODO Auto-generated method stub
				
			}

		};
		
		
		classReader.accept(classVisitor, 0);
		return result;
	}

	/**
	 * Get methods and their parameter names declared by a class.
	 * @param clazz
	 * @return
	 */
	public static Map<Method, String[]> getDeclaredMethodsParameterNames(Class<?> clazz) {
		if (! declaredMethodsParameterNamesCache.containsKey(clazz)) {
			synchronized (declaredMethodsParameterNamesCache) {
				if (! declaredMethodsParameterNamesCache.containsKey(clazz)) {
					Map<Method, String[]> result = doGetDeclaredMethodsParameterNames(clazz);
					declaredMethodsParameterNamesCache.put(clazz, result);
					return result;
				}
			}
		}
		return declaredMethodsParameterNamesCache.get(clazz);
	}
	
	private static Map<Class<?>, Map<Method, String[]>> methodsParameterNamesCache =
		new HashMap<Class<?>, Map<Method,String[]>>();
	
	private static Map<Method, String[]> doGetMethodsParameterNames(Class<?> clazz) {
		if (log.isDebugEnabled()) {
			log.debug("getMethodsParameterNames " + clazz);
		}
		Map<Method, String[]> result = new LinkedHashMap<Method, String[]>();
		Set<String> methodSignatures = new HashSet<String>();
		Class<?> tmpClass = clazz;
		while (tmpClass != null) {
			Map<Method, String[]> methods = getDeclaredMethodsParameterNames(tmpClass);
			for (Method method : methods.keySet()) {
				String methodSignature = getMethodSignature(method);
				if (! methodSignatures.contains(methodSignature)) {
					methodSignatures.add(methodSignature);
					result.put(method, methods.get(method));
				}
			}
			tmpClass = tmpClass.getSuperclass();
		}
		return result;
	}
	
	/**
	 * Get methods and their parameter names declared by a class and its super classes.
	 * 
	 * @param clazz
	 * @return
	 */
	public static Map<Method, String[]> getMethodsParameterNames(Class<?> clazz) {
		if (! methodsParameterNamesCache.containsKey(clazz)) {
			synchronized (methodsParameterNamesCache) {
				if (! methodsParameterNamesCache.containsKey(clazz)) {
					Map<Method, String[]> result = doGetMethodsParameterNames(clazz);
					methodsParameterNamesCache.put(clazz, result);
					return result;
				}
			}
		}
		return methodsParameterNamesCache.get(clazz);
	}
	
	private static Map<Class<?>, List<Method>> declaredMethodsInDeclaringOrderCache =
		new HashMap<Class<?>, List<Method>>();
	
	private static List<Method> doGetDeclaredMethodsInDeclaringOrder(final Class<?> clazz) {
		if (log.isDebugEnabled()) {
			log.debug("getDeclaredMethodsInDeclaringOrder " + clazz);
		}
		final List<Method> result = new ArrayList<Method>();
		
		ClassReader classReader;
		try {
			String classResourceName = "/" + clazz.getName().replace('.', '/') + ".class";
			InputStream classResourceInputStream = ReflectionUtils.class.getResourceAsStream(classResourceName);
			classReader = new ClassReader(classResourceInputStream);
		} catch (Exception ex) {
			return Arrays.asList(clazz.getDeclaredMethods());
		}
		
		ClassVisitor classVisitor = new ClassVisitor() {

			@Override
			public void visit(int version, int access, String name,
					String signature, String superName, String[] interfaces) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitSource(String source, String debug) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitOuterClass(String owner, String name, String desc) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public AnnotationVisitor visitAnnotation(String desc,
					boolean visible) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void visitAttribute(Attribute attr) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void visitInnerClass(String name, String outerName,
					String innerName, int access) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public FieldVisitor visitField(int access, String name,
					String desc, String signature, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MethodVisitor visitMethod(int access, String name,
					String desc, String signature, String[] exceptions) {
				if ("<init>".equals(name) || "<clinit>".equals(name)) {
					return null;
				}
				try {
					org.objectweb.asm.Type[] types = org.objectweb.asm.Type.getArgumentTypes(desc);
					Class<?>[] parameterTypes = new Class<?>[types.length];
					for (int i = 0; i < types.length; i++) {
						parameterTypes[i] = org.springframework.util.ClassUtils.forName(types[i].getClassName(), null);
					}
					
					Method method = clazz.getDeclaredMethod(name, parameterTypes);
					result.add(method);
					
					return null;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public void visitEnd() {
				// TODO Auto-generated method stub
				
			}
			
		};
		
		classReader.accept(classVisitor, 0);
		return result;
	}
	
	public static List<Method> getDeclaredMethodsInDeclaringOrder(Class<?> clazz) {
		if (! declaredMethodsInDeclaringOrderCache.containsKey(clazz)) {
			synchronized (declaredMethodsInDeclaringOrderCache) {
				if (! declaredMethodsInDeclaringOrderCache.containsKey(clazz)) {
					List<Method> result = doGetDeclaredMethodsInDeclaringOrder(clazz);
					declaredMethodsInDeclaringOrderCache.put(clazz, result);
					return result;
				}
			}
		}
		return declaredMethodsInDeclaringOrderCache.get(clazz);
	}
	
	private static Map<Class<?>, List<Method>> methodsInDeclaringOrderCache =
		new HashMap<Class<?>, List<Method>>();
	
	private static List<Method> doGetMethodsInDeclaringOrder(Class<?> clazz) {
		if (log.isDebugEnabled()) {
			log.debug("getMethodsInDeclaringOrder " + clazz);
		}
		List<Method> result = new ArrayList<Method>();
		Set<String> methodSignatures = new HashSet<String>();
		Class<?> tmpClass = clazz;
		while (tmpClass != null) {
			List<Method> methods = getDeclaredMethodsInDeclaringOrder(tmpClass);
			List<Method> temp = new ArrayList<Method>();
			for (Method method : methods) {
				String methodSignature = getMethodSignature(method);
				if (! methodSignatures.contains(methodSignature)) {
					methodSignatures.add(methodSignature);
					temp.add(method);
				}
			}
			result.addAll(0, temp);
			tmpClass = tmpClass.getSuperclass();
		}
		return result;
	}
	
	public static List<Method> getMethodsInDeclaringOrder(Class<?> clazz) {
		if (! methodsInDeclaringOrderCache.containsKey(clazz)) {
			synchronized (methodsInDeclaringOrderCache) {
				if (! methodsInDeclaringOrderCache.containsKey(clazz)) {
					List<Method> result = doGetMethodsInDeclaringOrder(clazz);
					methodsInDeclaringOrderCache.put(clazz, result);
					return result;
				}
			}
		}
		return methodsInDeclaringOrderCache.get(clazz);
	}
	
	private static Map<Method, String> methodSignatureCache = new HashMap<Method, String>();
	
	private static String doGetMethodSignature(Method method) {
		if (log.isDebugEnabled()) {
			log.debug("getMethodSignature " + method);
		}
		StringBuilder sb = new StringBuilder();
		sb.append(method.getName());
		sb.append("(");
		Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes.length > 0) {
			for (Class<?> parameterType : parameterTypes) {
				sb.append(org.springframework.util.ClassUtils.getQualifiedName(parameterType));
				sb.append(", ");
			}
			sb.setLength(sb.length() - 2);
		}
		sb.append(")");
		return sb.toString();
	}
	
	/**
	 * Generate method signature in the form <code>methodName[([arg_list])]</code>,
	 * where <code>arg_list</code> is a comma-separated list of fully-qualified
	 * type names.
	 * The result can be resolved by
	 * org.springframework.beans.BeanUtils.resolveSignature(String signature, Class clazz)
	 * 
	 * @param method
	 * @return
	 */
	public static String getMethodSignature(Method method) {
		if (! methodSignatureCache.containsKey(method)) {
			synchronized (methodSignatureCache) {
				if (! methodSignatureCache.containsKey(method)) {
					String result = doGetMethodSignature(method);
					methodSignatureCache.put(method, result);
					return result;
				}
			}
		}
		return methodSignatureCache.get(method);
	}
	
	
	public static Type getActualTypeArgument(Class<?> clazz, TypeVariable<?> typeVariable) {
		GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
		if (! (genericDeclaration instanceof Class)) {
			return null;
		}
		TypeVariable<?>[] typeVariables = genericDeclaration.getTypeParameters();
		Type[] actualTypeArguments = null;
		Type[] interfaceTypes = clazz.getGenericInterfaces();
		for (Type interfaceType : interfaceTypes) {
			if (interfaceType instanceof ParameterizedType) {
				actualTypeArguments = ((ParameterizedType) interfaceType).getActualTypeArguments();
				break;
			}
		}
		if (actualTypeArguments == null) {
			throw new RuntimeException("Actual type arguments not found");
		}
		if (typeVariables.length != actualTypeArguments.length) {
			throw new RuntimeException("Wrong number of actual type arguments");
		}
		for (int i = 0; i < typeVariables.length; i++) {
			if (typeVariables[i] == typeVariable) {
				return actualTypeArguments[i];
			}
		}
		throw new RuntimeException("Actual type argument for " + typeVariable + " not found");
	}
	
}
