package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import Dao.BetsClass;
import Model.FileEntity;
import Model.WinCashClass;

public class QlcPrizeDetail {
	public static List<FileEntity> getQlcDetail(List<BetsClass> list,int gameId,String drawNumber) throws Exception{
		List<FileEntity> result = new ArrayList<FileEntity>();
		for(BetsClass bc : list){
			int type = bc.getBet_type();
			long tax = 0;
			long income = 0;
			String numbers = bc.getNumbers();
			Map<String,String>  prizeDetail = getQlcPrizeDetail(type,numbers);
			if(null==prizeDetail){
				continue;
			}
			String level = prizeDetail.get("level");
			long prize = Long.parseLong(prizeDetail.get("prize"))*bc.getMul();
			int flag = 1;
			if(prize>10000){
				flag = 0;
				tax = prize*20;
			}
			income = prize*100-tax;
			WinCashClass wcc = new WinCashClass(gameId,1,drawNumber,bc.getFucai_req_ser()+"",bc.getFucai_serial_no()+"",
					bc.getTime(),flag,prize*100,tax,income,level);
			result.add(wcc);
		}
		return result;
	}
	private static Map<String,String> getQlcPrizeDetail(int type,String numbers) throws Exception{
		String level = "";
		List<Integer> number = new ArrayList<Integer>();
		List<Integer> tempNumber = null;
		List<Integer> winNumber = null;
		List<Integer> blue =GloableConfig.winNumber.subList(7,8);
		if(type == 1){	
			String[] strArray= numbers.substring(1, numbers.length()-1).split(",");
			for(String i:strArray){
				number.add(Integer.parseInt(i));
			}	
			tempNumber = deepClone(number) ;
			winNumber = deepClone(number) ;
			winNumber.retainAll(GloableConfig.winNumber);
			tempNumber.removeAll(winNumber);
			level = getQlcLevel(winNumber.size(),tempNumber.size(),winNumber.size(),winNumber.containsAll(blue),false); 
		}else{
			JSONObject js = JSONObject.fromObject(numbers);
			number = (List<Integer>) js.get("dan");
			int tempSize = 7 - number.size();
			List<Integer> redTuo = (List<Integer>) js.get("tuo");
			tempNumber =deepClone(redTuo) ;
			winNumber = deepClone(redTuo) ;
			winNumber.retainAll(GloableConfig.winNumber);
			tempNumber.removeAll(winNumber);
			number.retainAll(GloableConfig.winNumber);
			level = getQlcDanTuoLevel(number.size(),tempNumber.size(),winNumber.size(),redTuo.size()
					,number.containsAll(blue),number.containsAll(blue)||winNumber.containsAll(blue),false,tempSize);
		}
		if("".equals(level)){
			return null;
		}
		String[] info = level.substring(1).split("\\[|\\]");
		List<Integer> list1 = new ArrayList<Integer>();
		List<Integer> list2 = new ArrayList<Integer>();
		for(int i=0;i<info.length;i++){
			if(i%2==0){
				list1.add(Integer.parseInt(info[i]));
			}else{
				list2.add(Integer.parseInt(info[i]));
			}
		}
		Map<String,String> result = getPrize(list1,list2);
		return result;
	}
	private static String getQlcLevel(int winTempNumber,int tempNumber,int winNumber,boolean blueFlag,boolean flag ) throws Exception{
		String level = "";
		int mul = 1;
		if(!blueFlag){
			if(flag){
//				mul = winNumber+1;
				mul = Combinations.combinations(winNumber, winTempNumber);
			}
			switch(winTempNumber){
				case 4:{
					long count  = Combinations.combinations(tempNumber, 3);
					level += "[7]"+count*mul;
				}break;
				case 5:{
					long count = Combinations.combinations(tempNumber, 2);
					level += "[5]"+count*mul;
					if(tempNumber>2){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,blueFlag,true);
					}
				}break;
				case 6:{
					long count =  Combinations.combinations(tempNumber, 1);
					level += "[3]"+count*mul;
					if(tempNumber>1){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,blueFlag,true);
					}
				}break;
				case 7:{
					long count = 1;
					level +="[1]"+count;
					if(tempNumber>0){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,blueFlag,true);
					}
				}break;
				default :break;
			}
		}else{
			if(flag){
				mul = winTempNumber;
			}
			switch(winTempNumber){
				case 5:{
					long count  = Combinations.combinations(tempNumber, 2);
					level += "[6]"+count*mul;
					if(tempNumber>2){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,false,false);
					}
				}break;
				case 6:{
					long count = Combinations.combinations(tempNumber, 1);
					level += "[4]"+count*mul;
					if(tempNumber>1){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,false,false);
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,true,true);
					}
				}break;
				case 7:{
					level += "[2]"+1;
					if(tempNumber>0){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,false,false);
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,true,true);
					}
				}break;
				case 8:{
					level +="[1]1";
					if(tempNumber>0){
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,false,false);
						level += getQlcLevel(winTempNumber-1,tempNumber,winNumber,true,true);
					}
				}break;
				default :break;
			}
		}
		return level;
	}
	
	private static String getQlcDanTuoLevel(int winDan,int tempTuo,int winTuo,int tuo
			,boolean tempFlag,boolean blueFlag,boolean flag,int tempSize ) throws Exception{
		String level = "";
		int mul = 1;
		if(!blueFlag){
			if(flag){
//				mul = winNumber+1;
				mul = Combinations.combinations(tuo-tempTuo, winTuo);
			}
			switch(winDan+winTuo){
				case 4:{
					long count  = Combinations.combinations(tempTuo, tempSize-(4-winDan));
					level += "[7]"+count*mul;
				}break;
				case 5:{
					if(winDan!=5){
						long count = Combinations.combinations(tempTuo, tempSize-(4-winDan));
						level += "[5]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,blueFlag,true,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize);
				    	level += "[5]"+count*mul;
					}
				}break;
				case 6:{
					if(winDan!=6){
						long count = Combinations.combinations(tempTuo, tempSize-(6-winDan));
						level += "[3]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,blueFlag,true,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize);
				    	level += "[3]"+count*mul;
					}
				}break;
				case 7:{
					level += "[1]1";
	    			level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
			    				,tempFlag,blueFlag,true,tempSize );
				}break;
				default :break;
			}
		}else if(!tempFlag&&blueFlag){
			if(flag){
				mul = Combinations.combinations(tuo-tempTuo-1, winTuo-1);
			}
			switch(winDan+winTuo){
				case 5:{
					if(winDan!=4){
						long count  = Combinations.combinations(tempTuo, tempSize-(5-winDan));
						level += "[6]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize-1);
				    	level += "[6]"+count*mul;
				    	if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}
				}break;
				case 6:{
					if(winDan!=5){
						long count  = Combinations.combinations(tempTuo, tempSize-(6-winDan));
						level += "[4]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,true,true,tempSize );
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize-1);
				    	level += "[4]"+count*mul;
				    	if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}
				}break;
				case 7:{
					if(winDan!=6){
						long count  = Combinations.combinations(tempTuo, tempSize-(7-winDan));
						level += "[2]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,true,true,tempSize );
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize-1);
				    	level += "[2]"+count*mul;
				    	if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,false,false,tempSize );
				    	}
					}
				}break;
				case 8:{
					level +="[1]1";
					level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo,tempFlag,false,false,tempSize );
					level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo,tempFlag,true,false,tempSize );
				}break;
				default :break;
			}
		}else{
			if(flag){
				mul = Combinations.combinations(tuo-tempTuo, winTuo);
			}
			switch(winDan+winTuo){
				case 5:{
					if(winDan!=5){
						long count  = Combinations.combinations(tempTuo, tempSize-(5-winDan));
						level += "[6]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,true,false,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize);
				    	level += "[6]"+count*mul;
					}
				}break;
				case 6:{
					if(winDan!=6){
						long count  = Combinations.combinations(tempTuo, tempSize-(6-winDan));
						level += "[4]"+count*mul;
						if(tempTuo+winTuo-1>tempSize){
				    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
				    				,tempFlag,true,true,tempSize );
				    	}
					}else{
						long count = (long) Combinations.combinations(tempTuo, tempSize-1);
				    	level += "[4]"+count*mul;
					}
				}break;
				case 7:{
					long count  = Combinations.combinations(tempTuo, tempSize-(7-winDan));
					level += "[2]"+count*mul;
					if(tempTuo+winTuo-1>tempSize){
			    		level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo
			    				,tempFlag,true,true,tempSize );
			    	}
				}break;
				case 8:{
					level +="[1]1";
					level += getQlcDanTuoLevel(winDan,tempTuo,winTuo-1,tuo,tempFlag,true,true,tempSize );
				}break;
				default :break;
			}
		}
		return level;
	}
	private static Map<String,String> getPrize(List<Integer> list1,List<Integer> list2){
		int[] prize = {3000000,30000,2000,200,50,10,5};
		long income = 0;
		String level = "";
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		Map<String,String> result = new HashMap<String,String>();
		for(int i=0;i<list1.size();i++){
			int key = list1.get(i);
			if(map.get(key)!=null){
				int val = map.get(key);
				map.put(key, list2.get(i)+val);
			}else{
				map.put(key, list2.get(i));
			}
		}
		for(Map.Entry<Integer,Integer> set : map.entrySet()){
			level = level+ "["+set.getKey()+"]"+set.getValue();
			income += set.getValue()*prize[set.getKey()-1];
		}
		result.put("level",level);
		result.put("prize",income+"");
		return result;
	}
	private static List<Integer> deepClone(List<Integer> list){
		List<Integer> result  = new ArrayList<Integer>();
		for(int a:list){
			result.add(a);
		}
		return result;
	}
}
