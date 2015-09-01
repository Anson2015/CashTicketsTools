package Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBManager.DBManager;
import Model.FileEntity;
import Model.WinCashClass;
import Util.QlcPrizeDetail;
import Util.SsqPrizeDetail;
import Util.ThreeDPrizeDetail;

public class WinCashDao extends BaseDao{
//	游戏代码【Tab】虚拟终端【Tab】显示期号【Tab】请求序号【Tab】流水号【Tab】投注时间【Tab】自动返奖标志【Tab】中奖总金额【Tab】税金【Tab】实领奖金【Tab】奖等信息
	@Override
	public List<FileEntity> getDaoResult(int gameId, String drawNumber)  {
		List<FileEntity> list = null;
		try {
				DBManager instance = DBManager.getInstance();		
				Connection c = instance.getConnection();
				Statement st = c.createStatement();
				List<BetsClass> rsList = new ArrayList<BetsClass>();
				String sql = "select numbers,multiple,bet_type,fucai_serial_no,fucai_req_ser,bet_time from bets  where game_id = "+gameId+"and draw_number = '"+drawNumber+"'" ;
				ResultSet rs = st.executeQuery(sql);
				while(rs.next()){
					BetsClass bet = new BetsClass(rs.getInt("bet_type"), rs.getString("numbers"),
							rs.getLong("fucai_serial_no")	,rs.getLong("fucai_req_ser"), rs.getTimestamp("bet_time").getTime()+"",rs.getInt("multiple") );
					rsList.add(bet);
				}
				switch(gameId){
				case 2:list = SsqPrizeDetail.getSsqDetail(rsList, gameId, drawNumber);break;
				case 4:list = ThreeDPrizeDetail.get3DDetail(rsList, gameId, drawNumber);break;
				case 5:list = QlcPrizeDetail.getQlcDetail(rsList, gameId, drawNumber);break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
