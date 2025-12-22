package com.example.facturas_sinteticas_mongo.response;

import java.util.List;

public class AllBasicTemplateResponse {
	
	private List<BasicTemplateResponse> basicTemplates;

	public List<BasicTemplateResponse> getBasicTemplates() {
		return basicTemplates;
	}

	public void setBasicTemplates(List<BasicTemplateResponse> basicTemplates) {
		this.basicTemplates = basicTemplates;
	}
}
