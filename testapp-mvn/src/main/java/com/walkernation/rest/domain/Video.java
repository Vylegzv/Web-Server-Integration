package com.walkernation.rest.domain;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Video {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String name;

    /*@Persistent
    @Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
    private String imageType;*/

    @Persistent
    private Blob image;
    
    public Video(String name, Blob image) {
        this.name = name; 
        this.image = image;
    }

    public Long getId() {
        return key.getId();
    }

    public String getName() {
        return name;
    }

    /*public String getImageType() {
        return imageType;
    }*/

    public byte[] getImage() {
        if (image == null) {
            return null;
        }

        return image.getBytes();
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public void setImageType(String imageType) {
        this.imageType = imageType;
    }*/

    public void setImage(byte[] bytes) {
        this.image = new Blob(bytes);
    }
}
