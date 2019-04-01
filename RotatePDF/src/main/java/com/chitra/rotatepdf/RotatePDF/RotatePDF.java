package com.chitra.rotatepdf.RotatePDF;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class RotatePDF {

	public void pdfManipulate(String src, String dest) {
		try {
			PdfReader reader = new PdfReader(src);
			Rectangle pagesize = getPageSize(reader, 1);
			Document document = new Document(pagesize);
			
			PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(dest));
			document.isOpen();
			PdfContentByte cb = writer.getDirectContent();
			for(int i=1; i<=reader.getNumberOfPages(); i++) {
				pagesize = getPageSize(reader, i);
				document.setPageSize(pagesize);
				document.newPage();
				PdfImportedPage page = writer.getImportedPage(reader, i);
				if(isPortrait(reader, i)) {
					//If page is already portrait then make no changes to the template
					cb.addTemplate(page, 0,0);
				}else {
					//If page is not portrait then apply matrix transformation. https://en.wikipedia.org/wiki/Transformation_matrix
					cb.addTemplate(page, 0,1,-1,0, pagesize.getWidth(), 0);
				}
			}
			document.close();
			reader.close();
		}catch(IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public float calcScale(float pageDim, float A4Size) {
		return A4Size /pageDim;
	}
	
	public boolean isPortrait(PdfReader reader, int pagenumber) {
		Rectangle pagesize = reader.getPageSize(pagenumber);
		return pagesize.getHeight() > pagesize.getWidth();
	}
	
	public Rectangle getPageSize(PdfReader reader, int pagenumber) {
		Rectangle pagesize = reader.getPageSizeWithRotation(pagenumber);
		return new Rectangle(Math.min(pagesize.getWidth(),  pagesize.getHeight()), Math.max(pagesize.getWidth(), pagesize.getHeight()));
	}
	
	public static void main(String[] args) {
		

	}

}
