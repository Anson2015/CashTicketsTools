package Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Model.BullClass;
import Model.FileEntity;
import Util.GloableConfig;

public class BullDao extends BaseDao {

	@Override
	public List<FileEntity>  getDaoResult(int gameId, String drawNumber) {
		// TODO Auto-generated method stub
//		游戏代号(2) 期号(10，若不足补0) 中奖号码(48，若不足补FF，每个中奖号码占2位) 奖等(2) 中奖注数(8) 单注奖金(8，单位元)
		List<FileEntity> list = null;
		switch (gameId){
			case 2 :  list = getSsqResult(gameId,drawNumber); break;
			case 4 :  list =  get3DResult(gameId,drawNumber); break;
			case 5 :  list =  getQLCResult(gameId,drawNumber);break;
		}
		return list;
	}
	private List<FileEntity> getSsqResult(int gameId,String drawNumber){
		long[] prize = {1000000000,50000000,300000,20000,1000,500};
		String winNumber = getSsqNumber();
		List<FileEntity> list = new ArrayList<FileEntity>();
		for(int i=0;i<6;i++){
			BullClass bc = new BullClass(gameId+"", drawNumber, winNumber, i+1+"",(int)(Math.random()*Math.pow(10, i+1))+"", prize[i]+"");
			list.add(bc);
		}
		return list;
	}
	
	private String getSsqNumber(){
		String number = "";
		if(!GloableConfig.flag){
			List<Integer> list = new ArrayList<Integer>();	
			for(int i=0;i<6;i++){	
				int red = (int)(Math.random()*33);
				while(red == 0||list.contains(red)){
					red ++;
				}
				list.add(red);
			}
			Collections.sort(list) ;  
			int temp = (int)(Math.random()*16);
			int blue = temp == 0?1:temp;
			list.add(blue);
			for(int num:list){
				number = number + String.format("%02d", num);			
			}
			GloableConfig.winNumber = list;
		}else{
			for(int num:GloableConfig.winNumber){
				number = number + String.format("%02d", num);			
			}
		}
		return number;	
	}
	
	private List<FileEntity> get3DResult(int gameId,String drawNumber){
		String number = get3DNumber();
		int[] prize = {100000,32000,16000};
		List<FileEntity> list = new ArrayList<FileEntity>();
		for(int i=0;i<3;i++){
			BullClass bc = new BullClass(gameId+"",drawNumber,number,i+1+"",(int)(Math.random()*Math.pow(1000, i+1))+"", prize[i]+"");
			list.add(bc);
		}
		return list;
	}
	private String get3DNumber(){
		String result = "";
		if(!GloableConfig.flag){
			List<Integer> list = new ArrayList<Integer>();
			int temp =(int)(Math.random()*999);
			int bai = temp/100;
			temp = temp%100;
			int shi = temp/10;
			int ge = temp%10;	
			list.add(bai);
			list.add(shi);
			list.add(ge);
			GloableConfig.winNumber = list;
			 result = "0"+bai+"0"+shi+"0"+ge;
		}else{
			 for(int number : GloableConfig.winNumber){
				 result = result+"0"+number;
			 }
		}
		return result;
	}
	private List<FileEntity> getQLCResult(int gameId,String drawNumber){
		String number = getQLCNumber();
		int[] prize = {500000,200000,80000,20000,5000,1000,500};
		List<FileEntity> list = new ArrayList<FileEntity>();
		for(int i=0;i<7;i++){
			BullClass bc = new BullClass(gameId+"", drawNumber, number, i+1+"",(int)(Math.random()*Math.pow(10, i+1))+"", prize[i]+"");
			list.add(bc);
		}
		return list;
	}
	private String getQLCNumber(){
		String number = "";
		if(!GloableConfig.flag){
			List<Integer> list = new ArrayList<Integer>();	
			for(int i=0;i<7;i++){	
				int red = (int)(Math.random()*30);
				while(red == 0||list.contains(red)){
					red ++;
				}
				list.add(red);
			}
			Collections.sort(list);
			list.add((int)(Math.random()*30));
			GloableConfig.winNumber = list;
			for(int num:list){
				number = number + String.format("%02d", num);			
			}
		}else{
			for(int num:GloableConfig.winNumber){
				number = number + String.format("%02d", num);			
			}
		}
		return number;
	}
}
