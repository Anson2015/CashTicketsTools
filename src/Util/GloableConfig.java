package Util;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class GloableConfig {
	private static String path = "tool_config.properties";//TODO.
	private static GloableConfig instance;
	private static Properties p ;
	public static List<Integer> winNumber = null;
	public static boolean flag = false;
	
	public GloableConfig() throws Exception{
		p =  new Properties() ;
		p.load(new FileInputStream(new File("").getAbsolutePath()+File.separator+path));	
	}
	
	public static GloableConfig getInstance(){
		if(instance == null){
			try {
				instance = new GloableConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public  String getProperties(String s){
		String result = p.getProperty(s);
		return result;
	}
}
