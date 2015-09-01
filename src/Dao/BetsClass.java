package Dao;

public class BetsClass {
//	  numbers,bet_type,fucai_serial_no,fucai_req_ser,bet_time
	private String numbers;
	private long fucai_serial_no;
	private int mul;
	private long fucai_req_ser;
	private String time;
	
	public BetsClass(int bet_type,String numbers,long fucai_serial_no,long fucai_req_ser,String time,int mul){
		this.bet_type = bet_type;
		this.numbers = numbers;
		this.fucai_serial_no = fucai_serial_no;
		this.fucai_req_ser = fucai_req_ser;
		this.time = time;
		this.mul = mul;
	}
	
	private int bet_type;
	public int getBet_type() {
		return bet_type;
	}

	public void setBet_type(int bet_type) {
		this.bet_type = bet_type;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public long getFucai_serial_no() {
		return fucai_serial_no;
	}

	public void setFucai_serial_no(long fucai_serial_no) {
		this.fucai_serial_no = fucai_serial_no;
	}

	public long getFucai_req_ser() {
		return fucai_req_ser;
	}

	public void setFucai_req_ser(long fucai_req_ser) {
		this.fucai_req_ser = fucai_req_ser;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getMul() {
		return mul;
	}

	public void setMul(int mul) {
		this.mul = mul;
	}
}
