package au.com.unico.service;

import java.util.List;

import javax.ejb.Local;

import au.com.unico.model.GCD;

@Local
public interface GCDPersistenceBeanLocal {
	
	public void saveGCD(GCD gcd);
	
	public List<GCD> getGCDs();
	
}
