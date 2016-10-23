package com.jpmorgan.ctrac.bean;

public class FileDetails {

	
	public FileDetails(int collateralID, String fileName) {
		this.collateralID = collateralID;
		this.fileName = fileName;
	}
	private int collateralID;
	private String fileName;
	
	public int getCollateralID() {
		return collateralID;
	}
	public void setCollateralID(int collateralID) {
		this.collateralID = collateralID;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}		
}
