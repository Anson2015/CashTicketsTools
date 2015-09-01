package Util;

import java.util.List;

public class Combinations {	
	public static int combinations(int n ,int k) throws Exception{
		if(n<k){
			System.out.println(n+","+k);
			throw new Exception("组合参数异常");
		}
		int result = (int) (jiecheng(n)/(jiecheng(k)*jiecheng(n-k)));
		return result;
	}
	
 private static long jiecheng(int i) throws Exception{
	 	if(i == 0){
	 		return 1L;
	 	}else if(i<0){
	 		throw new Exception("阶乘不能为负数") ;
	 	}else{
	 		return i*jiecheng(i-1);
	 	}
 	}
}
