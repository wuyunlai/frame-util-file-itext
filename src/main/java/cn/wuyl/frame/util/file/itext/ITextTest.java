package cn.wuyl.frame.util.file.itext;

import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;

public class ITextTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] files = { "e:\\4.pdf", "e:\\5.pdf", "e:\\6.pdf" };  
        String savepath = "e:\\temp.zip";  
        mergePdfFiles(files, savepath);
	}
	
	public static boolean mergePdfFiles(String[] files, String newfile) {  
        boolean retValue = false;  
        Document document = null;  
        PdfReader reader = null;  
        try {  
//            reader = new PdfReader(files[0]);  
//            document = new Document(reader.getPageSize(1));  
            document = new Document(PageSize.LEGAL);  
            
            FileOutputStream fo = new FileOutputStream(newfile);
            ZipOutputStream zos = new ZipOutputStream(fo);
            zos.setLevel(java.util.zip.Deflater.BEST_COMPRESSION);
			zos.putNextEntry(new ZipEntry("merge.pdf"));
			
            PdfCopy copy = new PdfCopy(document, zos);  
            document.open();  
            for (int i = 0; i < files.length; i++) {  
                reader = new PdfReader(files[i]);  
                /** 
                 * 解决Exception in thread "main" java.lang.IllegalArgumentException: 
                 * PdfReader not opened with owner password 
                 *  
                 *  
                 */  
                java.lang.reflect.Field f = reader.getClass().getDeclaredField(  
                        "encrypted");  
                f.setAccessible(true);  
                f.set(reader, false);  
                
                int n = reader.getNumberOfPages();  
                for (int j = 1; j <= n; j++) {  
                    document.newPage();  
                    PdfImportedPage page = copy.getImportedPage(reader, j);  
                    copy.addPage(page);  
                }  
            }  
            retValue = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if(document != null)
            	document.close();  
        }  
        return retValue;  
    }  
}
