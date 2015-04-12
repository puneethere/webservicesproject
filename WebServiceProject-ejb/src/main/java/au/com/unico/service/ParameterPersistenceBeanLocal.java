package au.com.unico.service;

import java.util.List;

import javax.ejb.Local;

import au.com.unico.model.Pair;
import au.com.unico.model.Parameter;

@Local
public interface ParameterPersistenceBeanLocal {
	
	public void saveParameter(Parameter param);
	
	public List<Parameter> getParams();
	
	public void addToQueueAndSave(Pair pair);

}
