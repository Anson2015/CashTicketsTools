package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBManager.DBManager;
import Model.CheckClass;
import Model.FileEntity;

public class CheckDao extends BaseDao {
//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】类别【Tab】总票数【Tab】总金额
	@Override
	public List<FileEntity> getDaoResult(int gameId, String drawNumber) throws Exception {
		// TODO Auto-generated method stub

		List<FileEntity> list = new ArrayList<FileEntity>();
		DBManager instance = DBManager.getInstance();		
		Connection c = instance.getConnection();
		String sql = "select sum(price),count(*) from bets where game_id = "+ gameId +" and draw_number = '"+ drawNumber+"'" ;
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int count = 0;
		long price = 0;
		if(rs.next()){		
			price = rs.getLong(1);	
			count = rs.getInt(2);	
		}
		CheckClass cc1 = new CheckClass(gameId,1, drawNumber,1,count,price);
		CheckClass cc2 = new CheckClass(gameId,1,drawNumber,2,count==0?0:20,price==0?0:1000000);
		list.add(cc1);
		list.add(cc2);	
		return list;	
	}
}
