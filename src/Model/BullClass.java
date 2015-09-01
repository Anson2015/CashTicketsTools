package Model;


public class BullClass extends FileEntity {
//	gameId:String,sessionId:String,winNumber:String,level:String,count:String,unit_Prize:String
	
//	游戏代号(2) 期号(10，若不足补0) 中奖号码(48，若不足补FF，每个中奖号码占2位) 奖等(2) 中奖注数(8) 单注奖金(8，单位元)
	private String gameId;
	private String drawNumber;
	private String winNumber;
	private String level ;
	private String count;
	private String prize;
	
	public BullClass(String gameId,String drawNumber,String winNumber,String level,String count,String prize){
		this.gameId = gameId;
		this.drawNumber = drawNumber;
		this.winNumber = winNumber;
		this.level = level;
		this.count = count;
		this.prize = prize;
	}
	
	public String getGameId() {
		return String.format("%02d",Integer.parseInt(gameId));
	}
	public void setGameId(String gameId) {

		this.gameId = String.format("%02d",Integer.parseInt(gameId));
//				format();
	}
	public String getDrawNumber() {
		return  String.format("%10s", drawNumber).replaceAll("\\s", "0");
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public String getWinNumber() {
//		return String.format("%s48", winNumber).replaceAll("\\s", replacement);
		String temp = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF".substring(0,48-winNumber.length());
		return winNumber+temp;	
	}
	public void setWinNumber(String winNumber) {
		this.winNumber = winNumber;
	}
	public String getLevel() {
		return String.format(level);
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getCount() {
		return String.format("%08d", Integer.parseInt(count));
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getPrize() {
		return String.format("%8s",prize).replaceAll("\\s", "0");
	}
	public void setPrize(String prize) {
		this.prize = prize;
	}

	
	
}
