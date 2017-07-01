package it.netgrid.commons.data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface RestDao<R extends CrudObject<ID>, ID> {

	public R put(R object);
	public R get(ID key);
	public R post(R object);
	public R delete(R object);

	
	public Iterator<R> getAsIterator(Map<String, String> query);
	public List<R> get(Map<String, String> query);
	
}
