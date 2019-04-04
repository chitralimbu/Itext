package com.chitra.itextJunit.iTextJunit;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;

public interface TransformPdf {
	public void pdfTransform(String src, String dest);
	public float calcScale(float pageDim, float A4Size);
	public Rectangle getPageSize(PdfReader reader, int pagenumber);
	public boolean isPortrait(PdfReader reader, int pagenumber);
}
