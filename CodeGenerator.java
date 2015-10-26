package com.woqu.ugc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {
	public static String getInsertFields(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(",#{"+field+"}");
		}
		return sb.substring(1);
	}
	
	public static String getInsertListFields(Class<?> clazz, String listName, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(",#{"+listName+"[\"+i+\"]."+field+"}");
		}
		return sb.substring(1);
	}
	
	public static String getTableField(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(","+field);
		}
		return sb.substring(1);
	}
	
	public static String getUpdateSql(Class<?> clazz, String [] ignoreProperties) {
		List<String> fields = getFields(clazz, ignoreProperties);
		StringBuilder sb = new StringBuilder();
		for (String field : fields) {
			sb.append(","+field + "=#{" + field + "}");
		}
		return sb.substring(1);
	}
	
	/**
	 * 获取类的所有field name
	 * @param clazz
	 * @param ignoreProperties 忽略的字段名
	 * @return
	 */
	private static List<String> getFields(Class<?> clazz, String [] ignoreProperties) {
		Field fields[] = clazz.getDeclaredFields();//反射获取字段名和值，不获取父类的字段
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		List<String> resultFields = new ArrayList<String>();//字段名结果集
		for (Field field : fields) {
			String fieldName = field.getName();
			if (ignoreProperties != null && ignoreList.contains(fieldName)) {
				continue;
			}
			resultFields.add(fieldName);
		}
		return resultFields;
	}
	
	/**
	 * 获取同名不同类型的字段名
	 */
	private static List<String> getFieldsWithDiffType(Class<?> clazz1,Class<?> clazz2) {
		Field fields1[] = clazz1.getDeclaredFields();
		Field fields2[] = clazz2.getDeclaredFields();
		List<String> result=new ArrayList<String>();
		for (Field f1 : fields1) {
			for (Field f2 : fields2) {
				if (f1.getName().equals(f2.getName())){
					if(!f1.getGenericType().equals(f2.getGenericType())) {
						result.add(f1.getName());	
					}
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 获取fields1与fields2的diff, 只检查field name
	 */
	private static List<String> getFieldsDiff(Field [] fields1, Field [] fields2) {
		List<String> result=new ArrayList<String>();
		for (Field f1 : fields1) {
			boolean flag = false;
			for (Field f2 : fields2) {
				if (f1.getName().equals(f2.getName())){
					flag = true;
					break;
				}
			}
			if(!flag) result.add(f1.getName());
		}
		return result;
	}
	public static void genSetterCode(Class<?> clazz, String objName) {
		Field fields[] = clazz.getDeclaredFields();
		genSetterCode(objName, fields);
	}
	private static void genSetterCode(String objName,Field [] fields) {
		System.out.println("-------"+objName+"--------");
		for (Field field : fields) {
			String fname = field.getName();
			String uname = upperFirstLetter(fname);
			System.out.println(objName+".set"+uname+"("+fname+");");
		}
	}
	//获取字段差异信息
	public static Map<String,List<String>> getFieldsInfo(Class<?> clazz1,Class<?> clazz2) {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		Field fields1[] = clazz1.getDeclaredFields();
		Field fields2[] = clazz2.getDeclaredFields();
		
		map.put(clazz1.getSimpleName(), getFieldsDiff(fields1, fields2));
		map.put(clazz2.getSimpleName(), getFieldsDiff(fields2, fields1));
		map.put("diff type", getFieldsWithDiffType(clazz1, clazz2));
		
		return map;
	}
	
	private static String upperFirstLetter(String s) {
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
	public static void generateCode(Class<?> clazz,String objName) {
		System.out.println("-------"+clazz.getName()+"--------");
		Field fields[] = clazz.getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			String uname = upperFirstLetter(name);
			String s="";
			if (field.getGenericType()==int.class) {
				s=objName+".set"+uname+"(NumUtil.getPositiveInteger(request.getParameter(\""+name+"\"), 0));";
			}else if (field.getGenericType()==short.class) {
				s=objName+".set"+uname+"(castShort(request.getParameter(\""+name+"\")));";
			}else if (field.getGenericType()==long.class) {
				s=objName+".set"+uname+"(NumUtil.getPositiveLong(request.getParameter(\""+name+"\"), 0));";
			}else {
				s=objName+".set"+uname+"(request.getParameter(\""+name+"\"));";
			}
			System.out.println(s);
		}
	}
}
