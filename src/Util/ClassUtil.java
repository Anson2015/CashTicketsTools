package Util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {
	public static List<Method> getGetter(Class<?> c){
		Method[] m = c.getMethods();
		List<Method> methodList = new ArrayList<Method>();
		for(Method method : m){
			if(method.getName().startsWith("get")){
				methodList.add(method);
			}
		}
		return methodList;
	}
	public static List<Method> getSetter(Class<?> c){
		Method[] m = c.getMethods();
		List<Method> methodList = new ArrayList<Method>();
		for(Method method : m){
			if(method.getName().startsWith("set")){
				methodList.add(method);
			}
		}
		return methodList;
	}
}
