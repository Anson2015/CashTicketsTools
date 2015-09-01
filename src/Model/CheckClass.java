package Model;

public class CheckClass extends FileEntity{
	

//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】类别【Tab】总票数【Tab】总金额
	private int gameId;
	private int virtualTerminal;
	private String drawNumber;
	private int type;
	private long totalTicket;
	private long price;
	
	public CheckClass(int gameId, int virtualTerminal, String drawNumber,
			int type, long totalTicket, long price) {
		super();
		this.gameId = gameId;
		this.virtualTerminal = virtualTerminal;
		this.drawNumber = drawNumber;
		this.type = type;
		this.totalTicket = totalTicket;
		this.price = price;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(int totalTicket) {
		this.totalTicket = totalTicket;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
}
