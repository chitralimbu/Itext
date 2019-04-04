package com.chitra.itextJunit.iTextJunit;

public class PdfChecker {
	public Boolean rotIsZero(final Float angle) {
		return angle == 0f;
	}
	
	public Boolean validHeight(final Float height) {
		return height == 842.0f;
	}
	
	public Boolean validwidth(final Float width){
		return width == 595.0f;
	}
}
