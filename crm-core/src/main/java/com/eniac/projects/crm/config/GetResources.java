package com.eniac.projects.crm.config;

import java.util.ResourceBundle;

public class GetResources {

	public String getUploadPath(){
		
		ResourceBundle rb = ResourceBundle.getBundle("ApplicationResources");
		
		return rb.getString("uploadPath");
	}
}
