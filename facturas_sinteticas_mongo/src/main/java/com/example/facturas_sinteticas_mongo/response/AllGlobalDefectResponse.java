package com.example.facturas_sinteticas_mongo.response;

import java.util.List;

public class AllGlobalDefectResponse {
	
	private List<GlobalDefectResponse> defects;

	public List<GlobalDefectResponse> getDefects() {
		return defects;
	}

	public void setDefects(List<GlobalDefectResponse> defects) {
		this.defects = defects;
	}
}
