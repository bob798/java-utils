# java-utils

## word to pdf

转换代码
```java
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
```

pom 依赖
```xml
 <dependency>
    <groupId>fr.opensagres.xdocreport</groupId>
    <artifactId>org.apache.poi.xwpf.converter.pdf</artifactId>
    <version>1.0.6</version>
</dependency>
<dependency>
    <groupId>cn.lesper</groupId>
    <artifactId>iTextAsian</artifactId>
    <version>3.0</version>
</dependency>
```

遇到问题
1. 中文字符无法展示

解决方案
java中word转pdf遇到的中文字体问题
https://blog.csdn.net/bobozai86/article/details/100057822?spm=1001.2101.3001.6650.14&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-14-100057822-blog-83091061.pc_relevant_multi_platform_whitelistv2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7Edefault-14-100057822-blog-83091061.pc_relevant_multi_platform_whitelistv2&utm_relevant_index=19
java word转pdf的几种方法 https://blog.csdn.net/qwert678000/article/details/72770109
生成pdf设置中文字体出错 \simsun.ttc' with 'Identity-H' is not recognized或者type of font{0} is not recognized
https://blog.csdn.net/zhangtongpeng/article/details/100173633
itext lowagie version:'2.1.7' 引入 https://stackoverflow.com/questions/68993958/itext-lowagie-version2-1-7-will-support-the-chinese-trasulations-for-pdf