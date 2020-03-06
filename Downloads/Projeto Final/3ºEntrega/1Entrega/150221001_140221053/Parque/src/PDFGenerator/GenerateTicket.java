package PDFGenerator;

import Bilhetes.Bilhete;
import Bilhetes.Fatura;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Classe que gera os PDF's de fatura e bilhete
 *
 * @author João e Jorge
 */
public class GenerateTicket {

    /**
     * Constructor method for GenerateTicket
     */
    public GenerateTicket() {
    }

    /**
     * Method that generates a QR Code for a given bilhete
     *
     * @param bilhete bilhete 
     * @return qrcodeImage
     * @throws BadElementException
     */
    public Image generateQRCodeTicket(Bilhete bilhete) throws BadElementException {
        BarcodeQRCode barcodeQrcode = new BarcodeQRCode(bilhete.toString(), 1, 1, null);
        Image qrcodeImage = barcodeQrcode.getImage();

        return qrcodeImage;
    }

    /**
     * Method that generates a QR Code for a given fatura
     *
     * @param fatura fatura
     * @return qrcodeImage
     * @throws BadElementException
     */
    public Image generateQRCodeFatura(Fatura fatura) throws BadElementException {
        BarcodeQRCode barcodeQrcode = new BarcodeQRCode(fatura.toString(), 1, 1, null);
        Image qrcodeImage = barcodeQrcode.getImage();
        qrcodeImage.setAbsolutePosition(20, 500);
        qrcodeImage.scalePercent(100);
        return qrcodeImage;
    }

    /**
     * Method that generates the Bilhete into PDF
     * @param bilhete bilhete 
     */
    public void generateBilhetePdf(Bilhete bilhete) {
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Bilhete.pdf"));
            document.open();
            Image qrcodeImage = generateQRCodeTicket(bilhete);
            qrcodeImage.scalePercent(100);
            document.add(qrcodeImage);
            document.add(new Paragraph("Bilhete"));
            document.add(new Paragraph(bilhete.toString()));
            document.close();
            generateFaturaPdf(bilhete.getFatura());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }}
    /**
     * Method that generates the Fatura into PDF
     * @param fatura fatura
     */
    public void generateFaturaPdf(Fatura fatura) {
        try {
            Document document = new Document();
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("Fatura.pdf"));
            document.open();
            Image qrcodeImage = generateQRCodeFatura(fatura);
            qrcodeImage.setAbsolutePosition(20, 500);
            qrcodeImage.scalePercent(100);
            document.add(qrcodeImage);
            document.add(new Paragraph("Fatura"));
            document.add(new Paragraph(fatura.toString()));
            document.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
}
