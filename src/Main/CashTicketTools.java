package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Dao.BullDao;
import Dao.CheckDao;
import Dao.TicketDao;
import Dao.WinCashDao;
import FileManager.Entity2File;
import Model.BullClass;
import Model.FileEntity;
import Util.GloableConfig;

public class CashTicketTools {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		while(true){
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入gameId:");  
			int gameId = sc.nextInt();
	        System.out.println("输入gameId："+gameId);   
	        System.out.println("输入期号:");  
	        String drawNumber = sc.next();
	        System.out.println("输入期号："+drawNumber+"\n"); 
	        System.out.println("是否需要预设中奖号码：Y/N？");
	        GloableConfig.flag = "y".equalsIgnoreCase(sc.next());
	        if(GloableConfig.flag){
	        	System.out.println("请输入中奖号码并以逗号隔开（最后一位为特殊号码）3D默认顺序为百十个 \n");
		        String number = sc.next();
		         boolean flag = checkCurrentNumbers(gameId,number) ;
		        while(!flag){
		        	System.out.println("当前输入的中奖号码非法，请重新进行输入\n");
		        	String newNumber = sc.next();
		        	flag = checkCurrentNumbers(gameId,newNumber) ;
		        }
	        }
	        List<FileEntity> listBull = new BullDao().getDaoResult(gameId, drawNumber);
	        List<FileEntity> listTicket = new TicketDao().getDaoResult(gameId, drawNumber);
	        List<FileEntity> listWinCash = new WinCashDao().getDaoResult(gameId, drawNumber);
	        List<FileEntity> listCheck = new CheckDao().getDaoResult(gameId, drawNumber);
	        Entity2File.entity2BullFile(listBull, gameId+"", drawNumber);
	        Entity2File.entity2CheckFile(listCheck, gameId+"", drawNumber);
	        Entity2File.entity2TicketFile(listTicket, gameId+"", drawNumber);
	        Entity2File.entity2WinCashFile(listWinCash, gameId+"", drawNumber);
	        System.out.printf("%d=》%s当前开奖文件生成完成！\n",gameId,drawNumber);  
		}
	}
	private static boolean checkCurrentNumbers(int gameId,String numbers) throws Exception{
		String[] tempStr = numbers.split(",|，");
		List<Integer> list = new ArrayList<Integer>(); 
		for(String str : tempStr ){		
				list.add(Integer.parseInt(str));
		}
		if("2".equals(gameId+"")){
			if(list.size()!=7){
				return false;
			}else{
				int blue = list.get(6);
				List<Integer> tempList = list.subList(0, 6);
				Collections.sort(tempList);
				if((int)tempList.get(0)<1||(int)tempList.get(5)>33||(!checkRepeat(tempList))
						|| blue<1||blue>16){
					return false;
				}
				tempList.add(blue);
				GloableConfig.winNumber = tempList;
				return true;
			}
		}else if("4".equals(gameId+"")){
			if(list.size()!=3){
				return false;
			}
			List<Integer> tempList = list.subList(0, list.size());
			Collections.sort(tempList);
			if(tempList.get(0)<0||(tempList.get(2)>9)){
				return false;
			}
			GloableConfig.winNumber = list;
			return true;
		}else if("5".equals(gameId+"")){
			if(Collections.min(list)<1||Collections.max(list)>30||!checkRepeat(list)||list.size()!=8){
				return false;
			}
			GloableConfig.winNumber = list;
			return true;
		}else{
			throw new Exception("当前游戏种类不存在");
		}
	}
	
	/**
	 * 去重
	 * @param list
	 * @return
	 */
	private static boolean checkRepeat(List<Integer> list){
		Set<Integer> set = new HashSet<Integer>();
		for(int i : list){
			if(!set.add(i)){
				return false;
			}
		}
		return true;
	}
}
