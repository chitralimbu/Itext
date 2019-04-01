import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStream;

public class ExtractPdf {
	public static void main(String[] args) throws IOException {
		String path = "PATH";
		String path2 = "PATH";
		PdfReader reader = new PdfReader(path);
		for(int i = 0; i < reader.getXrefSize(); i++) {
			PdfObject pdfobj = reader.getPdfObject(i);
			if(pdfobj !=null) {
				if(pdfobj.isStream()) {
					PdfStream stream = (PdfStream) pdfobj;
					PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
					if(pdfsubtype!=null) {
						if(pdfsubtype.toString().equals(PdfName.IMAGE.toString())){
							byte[] img = PdfReader.getStreamBytesRaw((PRStream) stream);
							FileOutputStream out = new FileOutputStream(new File(path2 +  "\\image" + i + ".jpg"));
							out.write(img);
							out.flush();
							out.close();
						}
					}
				}
			}
		}
	}
}
