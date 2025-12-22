package com.example.facturas_sinteticas_mongo.response;

import java.util.List;

public class AllPromptGenerationResponse {
	
	private List<PromptGenerationResponse> prompts;

	public List<PromptGenerationResponse> getPrompts() {
		return prompts;
	}

	public void setPrompts(List<PromptGenerationResponse> prompts) {
		this.prompts = prompts;
	}	
}
