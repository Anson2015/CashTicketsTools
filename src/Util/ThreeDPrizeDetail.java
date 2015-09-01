package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import Dao.BetsClass;
import Model.FileEntity;
import Model.WinCashClass;

public class ThreeDPrizeDetail {
	public static List<FileEntity> get3DDetail(List<BetsClass> list,int gameId,String drawNumber) throws Exception{
		List<FileEntity> result = new ArrayList<FileEntity>();
		for(BetsClass bc : list){
			int type = bc.getBet_type();
			long tax = 0;
			long income = 0;
			String numbers = bc.getNumbers();
			Map<String,String>  prizeDetail = get3DPrizeDetail(type,numbers);
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
	private static Map<String,String> get3DPrizeDetail(int type,String numbers) throws Exception{
		String level = "";
		List<Integer> winNumber = GloableConfig.winNumber;
		switch(type){
			case 6:{
					JSONObject js = JSONObject.fromObject(numbers);				
					List<Integer> bai = (List<Integer>)js.get("hundreds");
					List<Integer> shi = (List<Integer>)js.get("tens");
					List<Integer> ge = (List<Integer>)js.get("ones");
					if(bai.contains(winNumber.get(0))&&shi.contains(winNumber.get(1))&&ge.contains(winNumber.get(2))){
						level +="[1]1";
					}
				}break;
			case 7:{
				List<Integer> number = new ArrayList<Integer>();
				String[] strArray= numbers.substring(1, numbers.length()-1).split(",");
				for(String i:strArray){
					number.add(Integer.parseInt(i));
				}
				if(number.contains(winNumber.get(0))&&number.contains(winNumber.get(2))){
					level +="[2]1";
				}
			}break;
			case 8:{
				List<Integer> number = new ArrayList<Integer>();
				String[] strArray= numbers.substring(1, numbers.length()-1).split(",");
				for(String i:strArray){
					number.add(Integer.parseInt(i));
				}
				if(number.contains(winNumber.get(0))&&number.contains(winNumber.get(1))&&number.contains(winNumber.get(2))){
					level +="[3]1";
				}
			}break;
			case 9:{
				int sum = Integer.parseInt(numbers.substring(1,numbers.length()-1));
				if(sum == (winNumber.get(0)+winNumber.get(1)+winNumber.get(2))){
					level +="[1]1";
				}
			}break;
			case 21:{
				int sum = Integer.parseInt(numbers.substring(1,numbers.length()-1));
				if(sum == (winNumber.get(0)*2+winNumber.get(2))){
					level +="[2]1";
				}
			}break;
			case 22:{
				int sum = Integer.parseInt(numbers.substring(1,numbers.length()-1));
				if(sum == (winNumber.get(0)+winNumber.get(1)+winNumber.get(2))){
					level +="[3]1";
				}
			}break;
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
	
	private static Map<String,String> getPrize(List<Integer> list1,List<Integer> list2){
		int[] prize = {1040,346,173};
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
}
