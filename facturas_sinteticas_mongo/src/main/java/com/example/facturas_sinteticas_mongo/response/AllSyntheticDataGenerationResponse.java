package com.example.facturas_sinteticas_mongo.response;

import java.util.List;

public class AllSyntheticDataGenerationResponse {
	
	private List<SyntheticDataGenerationResponse> synthetics;

	public List<SyntheticDataGenerationResponse> getSynthetics() {
		return synthetics;
	}

	public void setSynthetics(List<SyntheticDataGenerationResponse> synthetics) {
		this.synthetics = synthetics;
	}

}
