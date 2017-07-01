package it.netgrid.commons.ormlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import it.netgrid.commons.data.BulkService;
import it.netgrid.commons.data.CrudObject;
import it.netgrid.commons.data.CrudService;
import it.netgrid.commons.data.DataAdapter;

public abstract class TemplateBulkService<T extends CrudObject<ID>, ID>  implements BulkService<T, ID> {

	protected final ConnectionSource connection;
	protected final CrudService<T, ID> crudService;
	
	protected TemplateBulkService(ConnectionSource connection) {
		this.connection = connection;
		this.crudService = null;
	}
	
	protected TemplateBulkService(ConnectionSource connection, CrudService<T, ID> crudService) {
		this.connection = connection;
		this.crudService = crudService;
	}

	@Override
	public List<T> read(List<ID> ids) throws SQLException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ID> create(List<T> objects) throws SQLException, IllegalArgumentException {
		Integer affected = TransactionManager.callInTransaction(connection, new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				return createRaw(objects);
			}
			
		});
		
		return (affected > 0) ? this.getIds(objects) : this.getIds(null);
	}

	@Override
	public List<ID> update(List<T> objects) throws SQLException, IllegalArgumentException {
		Integer affected = TransactionManager.callInTransaction(connection, new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				return updateRaw(objects);
			}
			
		});

		return (affected > 0) ? this.getIds(objects) : this.getIds(null);

	}

	@Override
	public List<ID> delete(List<T> objects) throws SQLException, IllegalArgumentException {
		Integer affected = TransactionManager.callInTransaction(connection, new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				return deleteRaw(objects);
			}
			
		});

		return (affected > 0) ? this.getIds(objects) : this.getIds(null);
	}

	@Override
	public List<ID> deleteAll(List<ID> ids) throws SQLException, IllegalArgumentException {
		Integer affected = TransactionManager.callInTransaction(connection, new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				return deleteAllRaw(ids);
			}
			
		});

		return (affected > 0) ? ids : this.getIds(null);
	}
	
	@Override
	public int createRaw(List<T> list) throws SQLException, IllegalArgumentException {
		int affected = 0;
		
		for(T item : list) {
			affected += this.crudService.createRaw(item);
		}
		
		return affected;
	}

	@Override
	public int updateRaw(List<T> list) throws SQLException, IllegalArgumentException {
		int affected = 0;
		
		for(T item : list) {
			affected += this.crudService.updateRaw(item);
		}
		
		return affected;
	}

	@Override
	public int deleteRaw(List<T> list) throws SQLException, IllegalArgumentException {
		int affected = 0;
		
		for(T item : list) {
			affected += this.crudService.deleteRaw(item);
		}
		
		return affected;
	}

	@Override
	public int deleteAllRaw(List<ID> ids) throws SQLException, IllegalArgumentException {
		int affected = 0;
		
		for(ID item : ids) {
			T object = this.crudService.read(item);
			affected += this.crudService.deleteRaw(object);
		}
		
		return affected;
	}
	
	@Override
	public List<T> read(Map<String, Object> filter, Long pageSize, Long offset) throws SQLException, IllegalArgumentException {
		QueryBuilder<?, ID> query = this.initQueryBuilder();
		
		if(filter != null) {
			this.applyFilter(query, filter);
		}
		
		if(pageSize != null) {
			this.applyPageSize(query, pageSize);
		}
		
		if(offset != null) {
			this.applyOffset(query, offset);
		}
		
		List<T> retval = new ArrayList<T>();
		CloseableIterator<?> iterator = query.iterator();
		while(iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			CrudObject<ID> queryItem = (CrudObject<ID>) iterator.next();
			T item = this.crudService.read(queryItem.getId());
			retval.add(item);
		}
		
		return retval;
	}


	@Override
	public <D> List<D> read(DataAdapter<T, D> dataAdapter, Map<String, Object> filter, Long pageSize, Long offset) throws SQLException, IllegalArgumentException {
		QueryBuilder<?, ID> query = this.initQueryBuilder();
		
		if(filter != null) {
			this.applyFilter(query, filter);
		}
		
		if(pageSize != null) {
			this.applyPageSize(query, pageSize);
		}
		
		if(offset != null) {
			this.applyOffset(query, offset);
		}
		
		List<D> retval = new ArrayList<D>();
		CloseableIterator<?> iterator = query.iterator();
		while(iterator.hasNext()) {
			@SuppressWarnings("unchecked")
			CrudObject<ID> queryItem = (CrudObject<ID>) iterator.next();
			T item = this.crudService.read(queryItem.getId());
			D output = dataAdapter.getData(item);
			retval.add(output);
		}
		
		return retval;
	}
	

	public void applyPageSize(QueryBuilder<?, ID> query, Long pageSize) {
		query.limit(pageSize);
	}
	
	public void applyOffset(QueryBuilder<?, ID> query, Long offset) throws SQLException {
		query.offset(offset);
	}
	
	public abstract void applyFilter(QueryBuilder<?, ID> query, Map<String, Object> filter) throws SQLException;	
	
	public abstract QueryBuilder<?, ID> initQueryBuilder();


	private List<ID> getIds(List<T> items) {
		List<ID> retval = new ArrayList<ID>();
		
		if(items != null) {
			for(T item : items) {
				retval.add(item.getId());
			}
		}
		
		return retval;
	}
	
}
