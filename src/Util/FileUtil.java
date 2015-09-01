package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class FileUtil {
	/**
	 * @param file
	 * @param str
	 */
	public static void str2File(String file,String str){
		String filePath = file.substring(0,file.lastIndexOf(File.separator)+1);
		File files = new File(filePath);
		if(!files.exists()){
			files.mkdirs();
		}
		BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
                bufferedReader = new BufferedReader(new StringReader(str));
                bufferedWriter = new BufferedWriter(new FileWriter(file));
                char buf[] = new char[1024];         //字符缓冲区
                int len;
                while ((len = bufferedReader.read(buf)) != -1) {
                        bufferedWriter.write(buf, 0, len);
                }
                bufferedWriter.flush();
                bufferedReader.close();
                bufferedWriter.close();
        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                    try {
                            bufferedReader.close();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
            }
        } 
	}
	/**
	 * @param fileName
	 * @param filePath
	 */
//	public static void getFile(String fileName,String filePath){
//		File file = new File(filePath);
//		if(!file.exists()){
//			file.mkdirs();
//		}
//	}
}
