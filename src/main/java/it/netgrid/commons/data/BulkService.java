package it.netgrid.commons.data;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BulkService<T extends CrudObject<ID>, ID> {
	
	public List<T> read(Map<String, Object> filter, Long pageSize, Long offset) throws SQLException, IllegalArgumentException;
	public List<T> read(List<ID> ids) throws SQLException, IllegalArgumentException;
	
	public int create(List<T> objects) throws SQLException, IllegalArgumentException;
	public int update(List<T> objects) throws SQLException, IllegalArgumentException;
	public int delete(List<T> objects) throws SQLException, IllegalArgumentException;
	public int deleteAll(List<ID> ids) throws SQLException, IllegalArgumentException;
	
}
