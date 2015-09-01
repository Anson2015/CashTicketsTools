package Dao;

import java.util.List;

import Model.FileEntity;

public abstract class BaseDao {
	public abstract  List<FileEntity> getDaoResult(int gameId,String drawNumber) throws Exception;
}