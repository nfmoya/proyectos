/**
 * 
 */
package com.bbva.cfs.commons.utils;

/**
 * @author xah1257
 * 
 *         Enumeración
 */
public class EntityType {
	
	private final Long id;
	
	private EntityType(Long id){
		this.id = id;
	}
	
	public static final EntityType SCHOOL = new EntityType(Long.valueOf("0"));
	public static final EntityType ONG = new EntityType(Long.valueOf("1"));
	public static final EntityType PERSON = new EntityType(Long.valueOf("2"));

	public Long getId() {
		return id;
	}
}
