package com.chitra.itextJunit.generateTestData;

import java.io.File;
import com.chitra.itextJunit.iTextJunit.PdfToPortrait;
import com.chitra.itextJunit.iTextJunit.ScaleTo;

public class SubmitAction implements Runnable{

	private String source, destination, finalDest, temp;
	private File file;
	
	public SubmitAction(File file, String source, String destination, String temp) {
		this.source = source;
		this.destination = destination;
		this.temp = temp;
		this.file = file;
	}
	
	@Override
	public void run() {
		source = source + "\\" + file.getName();
		destination = destination+ "\\" + file.getName();
		finalDest = temp + "\\" + file.getName();
		
		new PdfToPortrait().pdfTransform(source, destination);
		new ScaleTo().pdfTransform(source, finalDest);
	}
	
}
