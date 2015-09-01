package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBManager.DBManager;
import Model.FileEntity;
import Model.TicketClass;

public class TicketDao extends BaseDao{
//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注总金额【Tab】投注时间
	@Override
	public List<FileEntity> getDaoResult(int gameId, String drawNumber) throws Exception {
		List<FileEntity> list = new ArrayList<FileEntity>();
		DBManager instance = DBManager.getInstance();		
		Connection c = instance.getConnection();
		Statement st = c.createStatement();
		String sql = "select fucai_serial_no,fucai_req_ser,price,bet_time from bets where game_id = "+gameId+" and draw_number = '"+drawNumber+"'";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()){
			TicketClass tc = new TicketClass(gameId, 1, drawNumber, rs.getString("fucai_req_ser")
					, rs.getString("fucai_serial_no"), Long.parseLong(rs.getString("price")), rs.getTimestamp("bet_time").getTime()+"");
			list.add(tc);
		}
		return list;
	}
}
