package com.example.arbetsprov;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;


public class FileUploader implements Receiver, SucceededListener {

	public File file; 
	
	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	
	private Path path; 
	
	private byte[] img; 
	
	private String fileName; 
	
	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		
		FileOutputStream fos = null;
		try {
			
			img = null; 
			
			this.fileName = filename; 
			
			file = new File(basepath + "/WEB-INF/uploads/" + filename); 
			fos = new FileOutputStream(file); 
			
			path = Paths.get(basepath + "/WEB-INF/uploads/" + filename); 
			
		}
		catch(final java.io.FileNotFoundException e){
			 new Notification("Could not open file<br/>",
                     e.getMessage(),
                     Notification.Type.ERROR_MESSAGE)
			 	.show(Page.getCurrent());
			 return null;
		}
		
		return fos; // Return the output stream to write to
	}
	
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		
		try {
			this.img = Files.readAllBytes(Paths.get(basepath + "/WEB-INF/uploads/" + "cykel.jpg"));
			
			new Notification("Could not open file<br/>",
                   "asds",
                    Notification.Type.ERROR_MESSAGE)
			 	.show(Page.getCurrent());
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
	}
	
	public byte[] getUploadedImage(){
		
		try {
			this.img = Files.readAllBytes(Paths.get(basepath + "/WEB-INF/uploads/" + this.fileName));
			
			return img; 
		}
		catch(Exception e){
			
		}
		
		return null; 
	}
	
	
	public void resetSettings(){
		this.img = null; 
		this.path = null; 
		this.fileName = null; 
		this.file = null;		
	}
	
}