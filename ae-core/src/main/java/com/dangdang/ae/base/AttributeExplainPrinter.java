package com.dangdang.ae.base;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 对象信息解析器。
 * 解析采用递归方式，直到对象中属性值得数据类型为：原始类型、原始类型封装类型、Enum、String、Date、Object时停止迭代。
 * 
 * @author zhangxiansheng
 *
 */
public class AttributeExplainPrinter {
	public static final String QUADRI_SPACE = "_Q_0_S_";
	public static final String NEW_LINE = "_N_0_L_";
	public static final String KEY_START_KEY = "_K_S_K_";
	public static final String KEY_END_KEY = "_K_E_K_";
	public static final String VALUE_START_KEY = "_V_S_K_";
	public static final String VALUE_END_KEY = "_V_E_K_";
	public static final String COMMENT_START_KEY = "_C_S_K_";
	public static final String COMMENT_END_KEY = "_C_E_K_";
	public static final String EMBRACE_START_KEY = "_E_S_K_";
	public static final String EMBRACE_END_KEY = "_E_E_K_";

	/**
	 * 最终展现形式的转换器，默认提供了html的转换器，其他形式的转换器可能还可以是markdown？
	 */
	private IMarkConvertor markConvertor;
	
	public AttributeExplainPrinter(IMarkConvertor convertor){
		this.markConvertor = convertor;
	}
	
	public AttributeExplainPrinter setConvertor(IMarkConvertor convertor){
		markConvertor = convertor;
		return this;
	}
	
	public String getExplainInfo(Object target){
		List<StringBuilder> infoArray =  explain(target);
		return markConvertor.convert(infoArray);
	}
	
	public static List<StringBuilder> explain(Object target){
		List<StringBuilder> infoList = new ArrayList<>();
		List<Field> fieldArray = getAllFields(target.getClass());
		for(Field field : fieldArray){
			if(field.getName().equals("serialVersionUID")){
				continue;
			}
			
			if(isSubclassOf(field.getType(), Collection.class) || field.getType().isArray()){
				processArray(field, target, infoList);
			}else if(isSubclassOf(field.getType(), Map.class)){
				processMap(field, target, infoList);
			}else if(!isPrimitiveType(field.getType())){
				processSelfDefinedClass(field, target, infoList);
			}else{
				processBasics(field, target, infoList);
			}			
		}
		
		return infoList;
	}
	
	private static void processArray(Field field, Object target, List<StringBuilder> infoList){
		Object o = getValueByField(field, target);
        
        if(field.getAnnotation(AttributeExplain.class) != null){
			infoList.add(createAnnotationInfo(field));
		}
        StringBuilder aInfo = new StringBuilder();
		aInfo.append(QUADRI_SPACE);
        aInfo.append(createKeyInfo(field));
        aInfo.append(" => ");
        aInfo.append(EMBRACE_START_KEY);
        aInfo.append("array (");
        aInfo.append(EMBRACE_END_KEY);
        infoList.add(aInfo);
        
        if(o != null){
        	Object[] array = null;
        	if(isSubclassOf(field.getType(), Collection.class)){
        		array = ((Collection<?>)o).toArray();
        	}else{
        		array = (Object[]) o;
        	}
        	
        	for(Object obj : array){
            	List<StringBuilder> childInfoList = explain(obj);
            	StringBuilder aInfo1 = new StringBuilder();
            	
            	//集合内为普通数据类型时，直接按照数组样式输出
            	if(isPrimitiveType(obj.getClass()) || obj.getClass().equals(Object.class)){
            		aInfo1.append(QUADRI_SPACE).append(QUADRI_SPACE);
            		aInfo1.append(VALUE_START_KEY);
            		aInfo1.append(array2String(array));
            		aInfo1.append(VALUE_END_KEY);
            		infoList.add(aInfo1);
            		break;
            	}
            	
				aInfo1.append(QUADRI_SPACE).append(QUADRI_SPACE);
				aInfo1.append("child => ");
				aInfo1.append(createStartBraceInfo());
				infoList.add(aInfo1);
				
				for(StringBuilder childInfo : childInfoList){
					childInfo.insert(0, QUADRI_SPACE).insert(0, QUADRI_SPACE);
				}
				infoList.addAll(childInfoList);
				
				infoList.add(createEndBraceDoubleSpaceInfo());
            }
        }
        
		infoList.add(createEndBraceInfo());
	}
	
	@SuppressWarnings("rawtypes")
	private static void processMap(Field field, Object target, List<StringBuilder> infoList){
		Object o = getValueByField(field, target);
        
        if(field.getAnnotation(AttributeExplain.class) != null){
			infoList.add(createAnnotationInfo(field));
		}
        StringBuilder aInfo = new StringBuilder();
		aInfo.append(QUADRI_SPACE);
        aInfo.append(createKeyInfo(field));
        aInfo.append(" => ");
        aInfo.append(EMBRACE_START_KEY);
        aInfo.append("map (");
        aInfo.append(EMBRACE_END_KEY);
        infoList.add(aInfo);
        
        if(o != null){
        	Map array = (Map)o;
        	
        	@SuppressWarnings("unchecked")
			Iterator<Entry> it = array.entrySet().iterator();
            while(it.hasNext()){
            	Entry obj = it.next();
            	StringBuilder aInfo1 = new StringBuilder();
            	
            	//集合内为普通数据类型时，直接按照数组样式输出
            	if(isPrimitiveType(obj.getValue().getClass()) || obj.getValue().getClass().equals(Object.class)){
            		aInfo1.append(QUADRI_SPACE).append(QUADRI_SPACE);
            		aInfo1.append(VALUE_START_KEY);
            		aInfo1.append(array.toString());
            		aInfo1.append(VALUE_END_KEY);
            		infoList.add(aInfo1);
            		break;
            	}
            	
            	List<StringBuilder> childInfoList = explain(obj.getValue());
            	
				aInfo1.append(QUADRI_SPACE).append(QUADRI_SPACE);
				aInfo1.append(KEY_START_KEY);
				aInfo1.append(obj.getKey());
				aInfo1.append(KEY_END_KEY);
				aInfo1.append(" => ");
				aInfo1.append(createStartBraceInfo());
				infoList.add(aInfo1);
				
				for(StringBuilder childInfo : childInfoList){
					childInfo.insert(0, QUADRI_SPACE).insert(0, QUADRI_SPACE);
				}
				infoList.addAll(childInfoList);
				
				infoList.add(createEndBraceDoubleSpaceInfo());
            }
        }
        
		infoList.add(createEndBraceInfo());
	}
	
	private static void processSelfDefinedClass(Field field, Object target, List<StringBuilder> infoList){
		if(field.getAnnotation(AttributeExplain.class) != null){
			infoList.add(createAnnotationInfo(field));
		}
		Object o = getValueByField(field, target);
		StringBuilder aInfo = new StringBuilder();
		aInfo.append(QUADRI_SPACE);
        aInfo.append(createKeyInfo(field));
		aInfo.append(" => ");
		aInfo.append(createStartBraceInfo());
		infoList.add(aInfo);
		if(o != null){
			List<StringBuilder> childInfoList = explain(o);
			for(StringBuilder childInfo : childInfoList){
				childInfo.insert(0, QUADRI_SPACE);
			}
			
			infoList.addAll(childInfoList);
		}
		
		infoList.add(createEndBraceInfo());
	}
	
	private static void processBasics(Field field, Object target, List<StringBuilder> infoList){
		if(field.getAnnotation(AttributeExplain.class) != null){
			infoList.add(createAnnotationInfo(field));
		}
		StringBuilder aInfo = new StringBuilder();
		aInfo.append(QUADRI_SPACE);
		aInfo.append(createKeyInfo(field));
		aInfo.append(" => ");
		
		
		Object o = getValueByField(field, target);
		aInfo.append(createValueInfo(o));
		infoList.add(aInfo);
	}
	
	private static Object getValueByField(Field field, Object target){
		Object o = null;
		try{
			if(field.getName().startsWith("is")){
				Method getMethod = target.getClass().getMethod(field.getName());
				if(getMethod != null){
					o = getMethod.invoke(target);
				}
			}else{
				PropertyDescriptor pd = new PropertyDescriptor(field.getName(), target.getClass());  
		        Method getMethod = pd.getReadMethod();
		        if(getMethod != null){
		        	o = getMethod.invoke(target);
		        }
			}
		}catch(IntrospectionException e){
			//
		}catch(IllegalAccessException e){
			//
		}catch(IllegalArgumentException e){
			//
		}catch(InvocationTargetException e){
			//
		}catch(NoSuchMethodException e){
			//
		}catch(SecurityException e){
			//
		}
		
        return o;
	}
	
	private static StringBuilder createAnnotationInfo(Field field){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(QUADRI_SPACE);
		sbuffer.append(COMMENT_START_KEY);
		sbuffer.append("//");
		sbuffer.append(field.getAnnotation(AttributeExplain.class).explaintInfo());
		sbuffer.append(COMMENT_END_KEY);
		return sbuffer;
	}
	
	private static StringBuilder createStartBraceInfo(){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(EMBRACE_START_KEY);
		sbuffer.append("(");
		sbuffer.append(EMBRACE_END_KEY);
		
		return sbuffer;
	}
	
	private static StringBuilder createEndBraceInfo(){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(QUADRI_SPACE);
		sbuffer.append(EMBRACE_START_KEY);
		sbuffer.append(")");
		sbuffer.append(EMBRACE_END_KEY);
		return sbuffer;
	}
	private static StringBuilder createEndBraceDoubleSpaceInfo(){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(QUADRI_SPACE).append(QUADRI_SPACE);
		sbuffer.append(EMBRACE_START_KEY);
		sbuffer.append(")");
		sbuffer.append(EMBRACE_END_KEY);
		return sbuffer;
	}
	
	private static StringBuilder createKeyInfo(Field field){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(KEY_START_KEY);
		sbuffer.append(field.getName());
		sbuffer.append(KEY_END_KEY);
		return sbuffer;
	}
	
	private static StringBuilder createValueInfo(Object o){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append(VALUE_START_KEY);
        if(o != null){
        	sbuffer.append(o);
        }else{
        	sbuffer.append("NULL");
        }
        sbuffer.append(VALUE_END_KEY);
        return sbuffer;
	}
	
	private static StringBuilder array2String(Object[] array){
		StringBuilder sbuffer = new StringBuilder();
		sbuffer.append("[");
		if(array != null){
			for(Object obj : array){
				if(obj != null){
					sbuffer.append(obj.toString());
					sbuffer.append(",");
				}
			}
		}
		sbuffer.append("]");
		return sbuffer;
	}
	
	/**
	 * 指定类型是否为基本类型：Number、Boolean、Character、Enum、String
	 * boolean、char、byte、short、int、long、float、double
	 * @param clazz
	 * @return
	 */
	private static boolean isPrimitiveType(Class<?> clazz){
		if(clazz == null){
			return false;
		}
		
		List<Class<?>> interfaces = getAllSuperclass(clazz);
		interfaces.add(clazz);//添加当前类型
		for(Class<?> interClass : interfaces){
			if(interClass.equals(Number.class) || interClass.equals(Boolean.class) || interClass.equals(Character.class)
					||interClass.isPrimitive() || interClass.isEnum() || interClass.equals(String.class) || interClass.equals(Date.class)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断是不是继承了某一个指定的接口或者类
	 * @param clazz1 当前类型
	 * @param clazz2 父类或者接口类型
	 * @return
	 */
	private static boolean isSubclassOf(Class<?> clazz1, Class<?> clazz2){
		if(clazz1 == null || clazz2 == null){
			return false;
		}
		
		List<Class<?>> allClazzArray = null;
		//获取父类型
		if(clazz2.isInterface()){
			allClazzArray = getAllInterfaces(clazz1);
		}else{
			allClazzArray = getAllSuperclass(clazz1);
		}
		//添加当前类型
		allClazzArray.add(clazz1);
		for(Class<?> interClass : allClazzArray){
			if(interClass.equals(clazz2)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取一个类继承链上所有的接口
	 * @param clazz
	 * @return
	 */
	private static List<Class<?>> getAllInterfaces(Class<?> clazz){
		List<Class<?>> interfaceArray = new ArrayList<>();
		if(clazz == null){
			return interfaceArray;
		}
		
		interfaceArray.addAll(Arrays.asList(clazz.getInterfaces()));
		//获取父类实现的接口
		if(clazz.getSuperclass() != null){
			interfaceArray.addAll(getAllInterfaces(clazz.getSuperclass()));
		}
		//获取父接口实现的接口
		if(clazz.getInterfaces() != null){
			for(Class<?> c : clazz.getInterfaces()){
				interfaceArray.addAll(getAllInterfaces(c));
			}
		}
		
		return interfaceArray;
	}
	
	/**
	 * 获取一个类型继承链上所有的父类，不包含自身。
	 * @param clazz
	 * @return
	 */
	private static List<Class<?>> getAllSuperclass(Class<?> clazz){
		List<Class<?>> superClassArray = new ArrayList<>();
		if(clazz == null){
			return superClassArray;
		}
		
		Class<?> superClass = clazz.getSuperclass();
		while(superClass != null){
			superClassArray.add(superClass);
			
			superClass = superClass.getSuperclass();
		}
		return superClassArray;
	}
	
	/**
	 * 获取某一个类所有的域，包含父类的域。
	 * 采用递归方式获取，先输出的是自Object以下的顶级父类的域,即自顶向下输出。
	 * @param clazz
	 * @return
	 */
	private static List<Field> getAllFields(Class<?> clazz){
		List<Field> fieldArray = new ArrayList<>();
		if(clazz == null || clazz == Object.class){
			return fieldArray;
		}
		
		if(clazz.getSuperclass() != null && !clazz.getSuperclass().equals(Object.class)){
			fieldArray.addAll(getAllFields(clazz.getSuperclass()));
		}
		fieldArray.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return fieldArray;
	}	
}
