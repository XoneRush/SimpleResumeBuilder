package com.alex.app.services;

import com.alex.app.model.Person;
import com.alex.app.utils.BreakStringUtil;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PersonService {
    public byte[] downloadResume() throws IOException {
        return Files.readAllBytes(Path.of("src/main/resources/static/doc.pdf"));
    }
    public void deleteResume(){
        new File("src/main/resources/static/doc.pdf").delete();
    }
    public void createResume(Person person){
        //logic генерация пдф
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);

            PDType0Font font = PDType0Font.load(doc, new File("src/main/resources/static/dejavu-sans.book.ttf"));


            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            Rectangle line = new Rectangle(25, 675, (int)(page.getArtBox().getWidth()*0.75), 2);
            System.out.println((int)page.getArtBox().getWidth());
            drawRect(contentStream, Color.BLACK, line, true);

            contentStream.beginText();

            contentStream.newLineAtOffset(25, 700);
            contentStream.setFont(font, 48);
            contentStream.setLeading(32.5f);
            showTextLn(person.getName() + " " + person.getLast_name(), contentStream);
            contentStream.newLine();

            contentStream.setFont(font, 16);
            contentStream.setLeading(16.5f);

            showTextLn("Контактные данные", contentStream);
            showTextLn(person.getEmail(), contentStream);
            showTextLn(person.getNumber(), contentStream);
            contentStream.newLine();

            showTextLn("Обо мне", contentStream);
            showTextLn("Дата рождения: " + person.getDate_of_birth().toGMTString(), contentStream);
            showTextLn("Город: " + person.getCity(), contentStream);
            contentStream.newLine();

            showTextLn("Опыт работы: ", contentStream);

            contentStream.endText();

            BreakStringUtil breakStringUtil = new BreakStringUtil(72, font, 16, contentStream, page);
            breakStringUtil.breakString(person.getExperience(), 25, 475);

            contentStream.close();

            doc.save("src/main/resources/static/doc.pdf");
            doc.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //Текст с переходом на следующую строку подобно println
    private void showTextLn(String message, PDPageContentStream pdPageContentStream) throws IOException {
        pdPageContentStream.showText(message);
        pdPageContentStream.newLine();
    }
    void drawRect(PDPageContentStream content, Color color,
                  Rectangle rect, boolean fill) throws IOException {
        content.addRect(rect.x, rect.y, rect.width, rect.height);
        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }

}
