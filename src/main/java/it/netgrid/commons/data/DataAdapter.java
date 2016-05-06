package it.netgrid.commons.data;

public interface DataAdapter<S, T> {

	void copyData(S source, T target);
	T getData(S source);
	
}
