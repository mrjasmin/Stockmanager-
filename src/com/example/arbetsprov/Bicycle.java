package com.example.arbetsprov;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bicycle implements Serializable {

	@Id  
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int ID; 
	
	private String articleID; 
	private String articleName; 
	private String articleType; 
	
	private byte[] image; 
	
	public Bicycle(){
		
	}
	
	public Bicycle(String articleID, String name, String type, byte[] img){
		this.articleID = articleID; 
		this.articleName = name; 
		this.articleType = type; 
		this.image = img; 
	}
	
	public int getID(){
		return this.ID; 
	}
	
	public String getArticleID(){
		return this.articleID; 
	}
	
	public String getArticleName(){
		return this.articleName;
	}
	
	public String getArticleType(){
		return this.articleType; 
	}
	
	public byte[] getImage(){
		return this.image; 
	}
	
	public void setID(int id){
		this.ID = id; 
	}
	
	public void setArticleID(String id){
		this.articleID = id; 
	}
	
	public void setArticleName(String name){
		this.articleName = name; 
	}
	
	public void setArticleType(String type){
		this.articleType = type; 
	}
	
	public void setImage(byte[] img){
		this.image = img; 
	}
	
}