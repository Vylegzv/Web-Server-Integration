package com.walkernation.rest.core.repository;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

/**
 * This class creates and returns an instance
 * of the PersistenceManager class.
 * 
 * @author Violetta Vylegzhanina
 *
 */
public class MyPersistenceManagerFactory {

	private static final PersistenceManagerFactory PMFInstance =
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private MyPersistenceManagerFactory(){}
	
	public static PersistenceManagerFactory get(){
		return  PMFInstance;
	}
}
