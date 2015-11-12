package com.example.arbetsprov;

import com.gargoylesoftware.htmlunit.javascript.host.Notification;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerItem;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.themes.ValoTheme;

public class ArticleForm extends FormLayout{


	Button save = new Button("Spara", this::save); 
	Button cancel = new Button("Cancel", this::cancel); 
			
	TextField articleID = new TextField("Article ID"); 
	TextField articleName = new TextField("Article Name"); 
	TextField articleType = new TextField("Article Type"); 
	
	Bicycle bicycle; 
	
	EntityItem<Bicycle> bicycleEntity; 
	
	Upload upload; 
	
	FileUploader receiver;

	Object ItemID; 	
	
	
	public ArticleForm(){
		configureComponents(); 
		buildLayout(); 
		
	}
	
	private void configureComponents(){
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false); 
	}
	
	private void buildLayout(){
		 setSizeUndefined();
	     setMargin(true);
	     
	     HorizontalLayout actions = new HorizontalLayout(save, cancel); 
	     actions.setSpacing(true);
	     
	     receiver = new FileUploader(); 

	     // Create the upload with a caption and set receiver later
	     upload = new Upload("Upload Image Here", receiver);
	     
	  
	     this.addComponents(actions, articleID, articleName, articleType, upload);
	     
	}
	
	
	public void save(Button.ClickEvent event){
		
		if(bicycleEntity != null){
			
		   ((ArbetsprovUI) UI.getCurrent()).getBicycles().removeItem(this.ItemID); 
		  
		}
		
		byte[] image; 
		
		if(receiver.getUploadedImage() == null && bicycleEntity != null){
			image = bicycleEntity.getEntity().getImage();
		}
		else {
			image = receiver.getUploadedImage(); 
		}
	
		Bicycle bicycle_new = new Bicycle(articleID.getValue(),articleName.getValue(),articleType.getValue(), image);   
			
		((ArbetsprovUI) UI.getCurrent()).getBicycles().addEntity(bicycle_new); 
		     
		setVisible(false); 
		
		articleID.setValue(""); 
		articleName.setValue("");
		articleType.setValue("");
		
		bicycleEntity = null; 
		
		receiver.resetSettings();
		
		image = null; 
		
	}
	
	
	public void cancel(Button.ClickEvent event){
		setVisible(false); 
		
		articleID.setValue(""); 
		articleName.setValue("");
		articleType.setValue("");
	}
	
	public void edit(EntityItem<Bicycle> bicycleItem, Object itemID){
		
		bicycleEntity = bicycleItem; 
		
		this.ItemID = itemID; 
		
		if(bicycleEntity != null){
			articleID.setValue(bicycleItem.getEntity().getArticleID()); 
			articleName.setValue(bicycleItem.getEntity().getArticleName());
			articleType.setValue(bicycleItem.getEntity().getArticleType());
		
		}
			
		setVisible(true); 
	}
	
	public void showForm(){
		
		articleID.setValue(""); 
		articleName.setValue("");
		articleType.setValue("");
		
		bicycleEntity = null; 
		setVisible(true); 
	}
	
	
	
	
}