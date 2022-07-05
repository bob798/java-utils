import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class Docx2PdfConversion {

	public static void main(String[] args) {
		try (InputStream is = new FileInputStream(new File("/Users/bob/Desktop/java.docx"));
				OutputStream out = new FileOutputStream(new File("/Users/bob/Desktop/rdtschools-Docx2PdfConverted_PDF_File.pdf"));) {
			long start = System.currentTimeMillis();
			// 1) Load DOCX into XWPFDocument
			XWPFDocument document = new XWPFDocument(is);
			// 2) Prepare Pdf options
			PdfOptions options = PdfOptions.create();
			options.fontProvider(new IFontProvider() {
				public com.lowagie.text.Font getFont(String familyName, String encoding, float size, int style, Color color) {
					try {
						// 统一设置中文字体，解决中文字符不展示问题
						BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
						com.lowagie.text.Font fontChinese = new Font(bfChinese, size, style, color);
						if (familyName != null)
							fontChinese.setFamily(familyName);
						return fontChinese;
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			});
			// 3) Convert XWPFDocument to Pdf
			PdfConverter.getInstance().convert(document, out, options);
			System.out.println("rdtschools-Docx2PdfConversion-word-sample.docx was converted to a PDF file in :: "
					+ (System.currentTimeMillis() - start) + " milli seconds");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}