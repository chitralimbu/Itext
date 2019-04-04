package com.chitra.itextJunit.iTextJunit;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfToPortrait implements TransformPdf{

	@Override
	public void pdfTransform(String src, String dest) {
		try{
			PdfReader reader = new PdfReader(src);
			Rectangle pagesize = getPageSize(reader, 1);
			Document document = new Document(pagesize);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			
			for(int i = 1; i <= reader.getNumberOfPages(); i++){
					
				Rectangle currentPagesize = getPageSize(reader, i);
				
				float widthScale = calcScale(currentPagesize.getWidth(), PageSize.A4.getWidth());
				float heightScale = calcScale(currentPagesize.getHeight(), PageSize.A4.getHeight());
				float newPageWidth = currentPagesize.getWidth() * widthScale;
				float newPageHeight = currentPagesize.getHeight() * heightScale;
				
				Rectangle newPageSize = new Rectangle(newPageWidth, newPageHeight);
				document.setPageSize(newPageSize);
				document.newPage();
				PdfImportedPage page = writer.getImportedPage(reader, i);
				
				cb.addTemplate(page, widthScale, 0, 0, heightScale, 0 , 0);
			}
			
			document.close();
			reader.close();
		}catch(IOException | DocumentException e){
			e.printStackTrace();
		}
	}

	@Override
	public float calcScale(float pageDim, float A4Size) {
		return A4Size / pageDim;
	}

	@Override
	public Rectangle getPageSize(PdfReader reader, int pagenumber) {
		Rectangle pagesize = reader.getPageSizeWithRotation(pagenumber);
		return new Rectangle(Math.min(pagesize.getWidth(), pagesize.getHeight()), Math.max(pagesize.getWidth(), pagesize.getHeight()));
	}
	
	@Override
	public boolean isPortrait(PdfReader reader, int pagenumber) {
		Rectangle pagesize = reader.getPageSize(pagenumber);
		return pagesize.getHeight() > pagesize.getWidth();
	}
}
