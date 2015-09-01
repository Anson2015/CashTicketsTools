package Model;

public class TicketClass extends FileEntity {
//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注总金额【Tab】投注时间
	private int gameId;
	private int virtualTerminal;
	private String drawNumber;
	private String reqNumber;
	private String ferNumber;
	private long totalPrice;
	private String time;
	
	public TicketClass(int gameId, int virtualTerminal, String drawNumber,
			String reqNumber, String ferNumber, long totalPrice, String time) {
		super();
		this.gameId = gameId;
		this.virtualTerminal = virtualTerminal;
		this.drawNumber = drawNumber;
		this.reqNumber = reqNumber;
		this.ferNumber = ferNumber;
		this.totalPrice = totalPrice;
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
