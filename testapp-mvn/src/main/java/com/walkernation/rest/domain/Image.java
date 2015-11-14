package com.walkernation.rest.domain;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@PersistenceCapable
public class Image {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    @Persistent
    Blob image;

    public Image() { }
    
    public Image(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }
    
    public Image(String name, byte[] bytes){
    	this.name = name;
    	this.image = new Blob(bytes);
    }
    
    public byte[] getImage(){
    	
    	if(image == null)
    		return null;
    	
    	return image.getBytes();
    }
    
    public void setImage(byte[] bytes) {
    	this.image = new Blob(bytes); 
    }
    
    /*public Long getId(){
    	return key.getId();
    }*/
    
	@JsonIgnore
	public Key getKey() {
		return key;
	}
    
	public String getId() {
		return KeyFactory.keyToString(getKey());
	}
    
    public String getName(){
    	return name;
    }
}



















