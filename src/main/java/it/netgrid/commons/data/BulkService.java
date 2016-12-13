package it.netgrid.commons.data;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BulkService<T extends CrudObject<ID>, ID> {
	
	public List<T> read(Map<String, Object> filter, Long pageSize, Long offset) throws SQLException, IllegalArgumentException;
	public List<T> read(List<ID> ids) throws SQLException, IllegalArgumentException;
	
	public List<ID> create(List<T> objects) throws SQLException, IllegalArgumentException;
	public List<ID> update(List<T> objects) throws SQLException, IllegalArgumentException;
	public List<ID> delete(List<T> objects) throws SQLException, IllegalArgumentException;
	public List<ID> deleteAll(List<ID> ids) throws SQLException, IllegalArgumentException;
	
	public int createRaw(List<T> object) throws SQLException, IllegalArgumentException;
	public int updateRaw(List<T> object) throws SQLException, IllegalArgumentException;
	public int deleteRaw(List<T> object) throws SQLException, IllegalArgumentException;
	public int deleteAllRaw(List<ID> ids) throws SQLException, IllegalArgumentException;
}
