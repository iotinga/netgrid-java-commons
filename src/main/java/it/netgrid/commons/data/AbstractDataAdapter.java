package it.netgrid.commons.data;

public abstract class AbstractDataAdapter<S, T> implements DataAdapter<S, T> {

	private final Class<T> clazz;
	
	public AbstractDataAdapter(Class<T> class1) {
		this.clazz = class1;
	}
	
	public AbstractDataAdapter() {
		clazz = null;
	}
	
	@Override
	public T getData(S source) {
		try {
			T t = this.getTargetInstance(source);
			this.copyData(source, t);
			return t;
		} catch (InstantiationException | IllegalAccessException e) {
			return null;
		}
	}
	
	@Override
	public void copyData(S source, T target) {
		if(source == null || target == null) return;
		this.doCopy(source, target);
	}
	
	protected T getTargetInstance(S source) throws InstantiationException, IllegalAccessException {
		return this.clazz.newInstance();
	}
	
	abstract protected void doCopy(S source, T target);

}
