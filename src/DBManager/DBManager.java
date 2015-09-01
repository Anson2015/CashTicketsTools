package DBManager;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBManager {
	private static DBManager instance;
	private static ComboPooledDataSource ds ;
	private static String c3p0Properties = "c3p0.properties";
	
	public DBManager() throws Exception {
		ds = new ComboPooledDataSource();
		Properties p = new Properties();
		p.load(new FileInputStream(new File("").getAbsolutePath()+File.separator+c3p0Properties));
		ds.setJdbcUrl(p.getProperty("c3p0.jdbcUrl"));
		ds.setDriverClass(p.getProperty("c3p0.driverClass"));
		ds.setUser(p.getProperty("c3p0.user"));
		ds.setPassword(p.getProperty("c3p0.password"));
		ds.setMaxPoolSize(Integer.parseInt(p.getProperty("c3p0.maxPoolSize")));
		ds.setMinPoolSize(Integer.parseInt(p.getProperty("c3p0.minPoolSize")));
		ds.setAcquireIncrement(Integer.valueOf(p.getProperty("c3p0.acquireIncrement")));    
        ds.setInitialPoolSize(Integer.valueOf(p.getProperty("c3p0.initialPoolSize")));    
        ds.setMaxIdleTime(Integer.valueOf(p.getProperty("c3p0.maxIdleTime"))); 
	}
	
	public static DBManager getInstance() throws Exception{    
        if(instance == null){
        	instance =  new DBManager();
        }  
        return instance;
    } 
	
	public Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
}
