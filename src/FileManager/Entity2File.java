package FileManager;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import Model.BullClass;
import Model.CheckClass;
import Model.FileEntity;
import Model.TicketClass;
import Model.WinCashClass;
import Util.FileUtil;
import Util.GloableConfig;

public class Entity2File {
	private static String path = GloableConfig.getInstance().getProperties("FilePath");
//	游戏代号(2) 期号(10，若不足补0) 中奖号码(48，若不足补FF，每个中奖号码占2位) 奖等(2) 中奖注数(8) 单注奖金(8，单位元)
	public static void entity2BullFile( List<FileEntity> list,String gameId,String drawNum  ) throws SQLException{
		StringBuilder sb = new StringBuilder();
		Boolean flag = true;
		for(FileEntity fe : list) {
			BullClass bc = (BullClass)fe;
			if(flag){		
				sb.append(bc.getGameId()+" ");
				sb.append(bc.getDrawNumber()+" ");
				sb.append(bc.getWinNumber()+"\n");
				flag = false;
			}
			sb.append(bc.getLevel()+" ");
			sb.append(bc.getCount()+" ");
			sb.append(bc.getPrize()+"\n");
		}
		String fileName = getFileName("Bull",gameId,drawNum);
		String filePath = path + File.separator + fileName;
		FileUtil.str2File(filePath, sb.toString());
	}
	public static void entity2CheckFile(List<FileEntity> list,String gameId,String drawNum ){
//		游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】类别【Tab】总票数【Tab】总金额
		StringBuilder sb = new StringBuilder();
		for(FileEntity fe : list) {
			CheckClass cc =(CheckClass)fe;
			sb.append(cc.getGameId()+"\t");
			sb.append(cc.getVirtualTerminal()+"\t");
			sb.append(cc.getDrawNumber()+"\t");
			sb.append(cc.getType()+"\t");
			sb.append(cc.getTotalTicket()+"\t");
			sb.append(cc.getPrice());
			sb.append("\n");
		}
		String fileName = getFileName("Check",gameId,drawNum);
		String filePath = path + File.separator + fileName;
		FileUtil.str2File(filePath, sb.toString());
	}
	public static void entity2TicketFile(List<FileEntity> list,String gameId,String drawNum){
//		游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注总金额【Tab】投注时间
		StringBuilder sb = new StringBuilder();
		for(FileEntity fe : list) {
			TicketClass tc =(TicketClass)fe;
			sb.append(tc.getGameId()+"\t");
			sb.append(tc.getVirtualTerminal()+"\t");
			sb.append(tc.getDrawNumber()+"\t");
			sb.append(tc.getReqNumber()+"\t");
			sb.append(tc.getFerNumber()+"\t");
			sb.append(tc.getTotalPrice()+"\t");
			sb.append(tc.getTime()+"\t");
			sb.append("\n");
		}
		String fileName = getFileName("Ticket",gameId,drawNum);
		String filePath = path + File.separator + fileName;
		FileUtil.str2File(filePath, sb.toString());
	}
	public static void entity2WinCashFile(List<FileEntity> list,String gameId,String drawNum){
//		游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注时间【Tab】自动返奖标志【Tab】中奖总金额【Tab】税金【Tab】实领奖金【Tab】奖等信息
		StringBuilder sb = new StringBuilder();
		for(FileEntity fe : list) {
			WinCashClass wc =(WinCashClass)fe;
			sb.append(wc.getGameId()+"\t");
			sb.append(wc.getVirtualTerminal()+"\t");
			sb.append(wc.getDrawNumber()+"\t");
			sb.append(wc.getReqNumber()+"\t");
			sb.append(wc.getFerNumber()+"\t");
			sb.append(wc.getTime()+"\t");
			sb.append(wc.getFlag()+"\t");
			sb.append(wc.getTotalPrize()+"\t");
			sb.append(wc.getTax()+"\t");
			sb.append(wc.getIncome()+"\t");
			sb.append(wc.getPrizeDetail());
			sb.append("\n");
		}
		String fileName = getFileName("WinCash",gameId,drawNum);
		String filePath = path + File.separator + fileName;
		FileUtil.str2File(filePath, sb.toString());
	}
	private static String getFileName(String type,String gameId,String drawNum){
		return type+String.format("%02d",Integer.parseInt(gameId))+".1."+drawNum+".txt";
	}
}
