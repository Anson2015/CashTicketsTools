package Model;

public class WinCashClass extends FileEntity {
//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注时间【Tab】自动返奖标志【Tab】中奖总金额【Tab】税金【Tab】实领奖金【Tab】奖等信息
	private int gameId;
	private int virtualTerminal;
	private String drawNumber;
	private String reqNumber;
	private String ferNumber;
	private String time;
	private int  flag;
	private long totalPrize;
	private long tax;
	private long income;
	private String prizeDetail;
	
	
	public WinCashClass(int gameId, int virtualTerminal, String drawNumber,
			String reqNumber, String ferNumber, String time, int flag,
			long totalPrize, long tax, long income, String prizeDetail) {
		super();
		this.gameId = gameId;
		this.virtualTerminal = virtualTerminal;
		this.drawNumber = drawNumber;
		this.reqNumber = reqNumber;
		this.ferNumber = ferNumber;
		this.time = time;
		this.flag = flag;
		this.totalPrize = totalPrize;
		this.tax = tax;
		this.income = income;
		this.prizeDetail = prizeDetail;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getVirtualTerminal() {
		return virtualTerminal;
	}
	public void setVirtualTerminal(int virtualTerminal) {
		this.virtualTerminal = virtualTerminal;
	}
	public String getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public String getReqNumber() {
		return reqNumber;
	}
	public void setReqNumber(String reqNumber) {
		this.reqNumber = reqNumber;
	}
	public String getFerNumber() {
		return ferNumber;
	}
	public void setFerNumber(String ferNumber) {
		this.ferNumber = ferNumber;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public long getTotalPrize() {
		return totalPrize;
	}
	public void setTotalPrize(long totalPrize) {
		this.totalPrize = totalPrize;
	}
	public long getTax() {
		return tax;
	}
	public void setTax(long tax) {
		this.tax = tax;
	}
	public long getIncome() {
		return income;
	}
	public void setIncome(long income) {
		this.income = income;
	}
	public String getPrizeDetail() {
		return prizeDetail;
	}
	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}
}
