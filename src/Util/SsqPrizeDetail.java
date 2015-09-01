package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import Dao.BetsClass;
import Model.FileEntity;
import Model.WinCashClass;

public class SsqPrizeDetail {
	public static List<FileEntity> getSsqDetail(List<BetsClass> list,int gameId,String drawNumber) throws Exception{
		List<FileEntity> result = new ArrayList<FileEntity>();
		for(BetsClass bc : list){
			int type = bc.getBet_type();
			long tax = 0;
			long income = 0;
			String numbers = bc.getNumbers();
			Map<String,String>  prizeDetail = getSsqPrizeDetail(type,numbers);
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
	private static Map<String,String> getSsqPrizeDetail(int type,String numbers) throws Exception{	
		String level = "";
		JSONObject js = JSONObject.fromObject(numbers);
		if(type == 1){		
			List<Integer> red = (List<Integer>) js.get("red");
			List<Integer> tempWinRed = deepClone(red);
			List<Integer> tempRed = deepClone(red);
			List<Integer> tempWinBlue = (List<Integer>) js.get("blue");
			List<Integer> blue = deepClone(tempWinBlue);
			List<Integer> tempBlue = deepClone(tempWinBlue);
			List<Integer> winRed = GloableConfig.winNumber.subList(0, 6);
			List<Integer> winBlue = GloableConfig.winNumber.subList(6,7);
			tempWinRed.retainAll(winRed);
			tempWinBlue.retainAll(winBlue);
			tempRed.removeAll(tempWinRed);
			tempBlue.removeAll(tempWinBlue);
			level = getSsqLevel(tempWinRed.size(),tempWinBlue.size(),red.size(),blue.size(),tempRed.size(),tempBlue.size(),false,tempWinRed.size());
		}else{
			List<Integer> redDan = (List<Integer>) js.get("redDan");
			List<Integer> redTuo = (List<Integer>) js.get("redTuo");
			List<Integer> winRed = GloableConfig.winNumber.subList(0, 6);
			List<Integer> winBlue = GloableConfig.winNumber.subList(6,7);
			List<Integer> tempWinRedTuo = deepClone(redTuo);//拖中奖
			List<Integer> tempRedTuo = deepClone(redTuo);//拖未中奖
			int tempSize =6-redDan.size();
			redDan.retainAll(winRed);
			tempWinRedTuo.retainAll(winRed);
			tempRedTuo.removeAll(tempWinRedTuo);
			List<Integer> tempWinBlue = (List<Integer>) js.get("blue");
			List<Integer> blue = deepClone(tempWinBlue);
			List<Integer> tempBlue = deepClone(tempWinBlue);
			tempWinBlue.retainAll(winBlue);
			tempBlue.remove(tempWinBlue);
			level = getSsqDanTuoLevel(redDan.size(),tempRedTuo.size()
					,tempWinRedTuo.size(),tempRedTuo.size(),tempWinBlue.size(),tempBlue.size(),false,tempSize);
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
	private static  String getSsqLevel (int winTempRed,int winBlue,int red,int blue,int tempRed,int tempBlue,Boolean flag,int winRed) throws Exception{
		String level = "";
		int mul = 1;
		if(flag){
			mul = Combinations.combinations(winRed, winTempRed);
		}
		switch(winBlue){
			case 1: {
				if(tempBlue == 0){
					switch(winTempRed){
						case 1:{
					    	long count = (long) Combinations.combinations(tempRed, 5);
					    	level += "[6]"+count*mul;
					    	if(tempRed>5){
								level += getSsqLevel(winTempRed-1,1,red,blue,tempRed,0,true,winRed);
							}
					    }break;
					    case 2:{
					    	long count = (long) Combinations.combinations(tempRed, 4);
					    	level += "[6]"+count*mul;
					    	if(tempRed>4){
								level += getSsqLevel(winTempRed-1,1,red,blue,tempRed,0,true,winRed);
							}
					    }break;
						case 3:{
							long count = (long) Combinations.combinations(tempRed, 3);
							level += "[5]"+count*mul;
							if(tempRed>3){
								level += getSsqLevel(winTempRed-1,1,red,blue,tempRed,0,true,winRed);
							}
						}break;
						case 4:{
							if(tempRed == 2){
								long count = 1L;
								level += "[4]"+count*mul;
							}else{
								long count = Combinations.combinations(tempRed, 2);
								level += "[4]"+count*mul;
								level += getSsqLevel(winTempRed-1,1,red,blue,tempRed,0,true,winRed);
							}
						}break;
						case 5:{
							if(tempRed == 1){
								long count = 1;
								level += "[3]"+count*mul;
							}else{
								long count = tempRed;
								level += "[3]"+count*mul;
								level += getSsqLevel(winTempRed-1,1,red,blue,tempRed,0,true,winRed);
							}
						}break;
						case 6:{
							if(tempRed == 0){
								level += "[1]"+1;
							}if(tempRed>0){
								level += "[1]"+1+ getSsqLevel(winTempRed-1,winBlue,red,blue,tempRed,0,true,winRed);
							}
						}break;
						default :{
							long count = (long) Combinations.combinations(tempRed, 6);
					    	level += "[6]"+count*mul;
						}
					}
				}else{
					level += getSsqLevel(winTempRed,winBlue,red,blue,tempRed,0,false,winRed);
					level += getSsqLevel(winTempRed,0,red,blue,tempRed,tempBlue,false,winRed);
				}
			}break;
			default:	{
				switch(winTempRed){
					case 4:{
						long count  = Combinations.combinations(tempRed, 2)*tempBlue;
						level += "[5]"+count*mul;
					}break;
					case 5:{
						if(tempRed == 1){
							long count = Combinations.combinations(tempRed, 1)*Combinations.combinations(winTempRed,5)*tempBlue;
							level += "[4]"+count*mul;
						}else{
							long count = Combinations.combinations(tempRed, 1)*Combinations.combinations(winTempRed,5)*tempBlue;
							level += "[4]"+count*mul;
							level += getSsqLevel(winTempRed-1,winBlue,red,blue,tempRed,tempBlue,true,winRed);
						}
					}break;
					case 6:{
						if(tempRed == 0){
							level += "[2]"+1;
						}if(tempRed>0){
							level += "[2]"+1+ getSsqLevel(winTempRed-1,winBlue,red,blue,tempRed,tempBlue,true,winRed);
						}
					}break;
					default : break;
				}
			}
		}
		return level;
	}
	
	private static Map<String,String> getPrize(List<Integer> list1,List<Integer> list2){
		int[] prize = {5000000,200000,3000,200,10,5};
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
	
	private static String getSsqDanTuoLevel(int winDan,int tempRedTuo
			,int winRedTuo,int redTuo,int winBlue,int tempBlue,boolean flag,int tempSize) throws Exception{
		String level = "";
		int mul = 1;
		if(flag){
			mul = Combinations.combinations(redTuo-tempRedTuo,winRedTuo );
		}
		switch(winBlue){
			case 1: {
				if(tempBlue == 0){
					switch(winDan+winRedTuo){
						case 1:{					    	
					    	if(winDan!=1){
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize-1);
						    	level += "[6]"+count*mul;
						    	if(tempRedTuo+winRedTuo-1>tempSize){
						    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
						    	}
							}else{
								long count = (long) Combinations.combinations(tempRedTuo, tempSize);
						    	level += "[6]"+count*mul;
							}
					    }break;
					    case 2:{
					    	if(winDan!=2){
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize-(2-winDan));
						    	level += "[6]"+count*mul;
						    	if(tempRedTuo+winRedTuo-1>tempSize){
						    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
						    	}
					    	}else{
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize);
						    	level += "[6]"+count*mul;
					    	}
					    }break;
						case 3:{
							if(winDan!=3){
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize-(3-winDan));
						    	level += "[5]"+count*mul;
						    	if(tempRedTuo+winRedTuo-1>tempSize){
						    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
						    	}
					    	}else{
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize);
						    	level += "[5]"+count*mul;
					    	}
						}break;
						case 4:{
							if(winDan!=4){
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize-(4-winDan));
						    	level += "[4]"+count*mul;
						    	if(tempRedTuo+winRedTuo-1>tempSize){
						    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
						    	}
					    	}else{
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize);
						    	level += "[4]"+count*mul;
					    	}
						}break;
						case 5:{
							if(winDan != 5){
								long count = (long) Combinations.combinations(tempRedTuo, tempSize-(5-winDan));
						    	level += "[3]"+count*mul;
						    	if(tempRedTuo+winRedTuo-1>tempSize){
						    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
						    	}
							}else{
					    		long count = (long) Combinations.combinations(tempRedTuo, tempSize);
						    	level += "[3]"+count*mul;
					    	}
						}break;
						case 6:{
							long count = (long) Combinations.combinations(tempRedTuo, tempSize-(6-winDan));
					    	level += "[3]"+count*mul;
					    	if(tempRedTuo+winRedTuo-1>tempSize){
					    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
					    	}
						}break;
						default :{
							long count = (long) Combinations.combinations(tempRedTuo, tempSize);
					    	level += "[6]"+count*mul;
						}
					}
				}else{
					level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo,redTuo,0,tempBlue,true,tempSize);
					level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo,redTuo,winBlue,0,true,tempSize);
				}
			}break;
			default:	{
				switch(winDan+winRedTuo){
					case 4:{
							long count = (long) Combinations.combinations(tempRedTuo, tempSize-(4-winDan));
					    	level += "[5]"+count*mul;
					}break;
					case 5:{
						if(winDan != 5){
							long count = (long) Combinations.combinations(tempRedTuo, tempSize-(5-winDan));
					    	level += "[4]"+count*mul;
					    	if(tempRedTuo+winRedTuo-1>tempSize){
					    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
					    	}
						}else{
				    		long count = (long) Combinations.combinations(tempRedTuo, tempSize);
					    	level += "[4]"+count*mul;
				    	}
					}break;
					case 6:{
						long count = (long) Combinations.combinations(tempRedTuo, tempSize-(6-winDan));
				    	level += "[2]"+count*mul;
				    	if(tempRedTuo+winRedTuo-1>tempSize){
				    		level += getSsqDanTuoLevel(winDan,tempRedTuo,winRedTuo-1,redTuo,winBlue,tempBlue,true,tempSize);
				    	}
					}break;
					default : break;
				}
			}
		}
		return level;
	}
}
