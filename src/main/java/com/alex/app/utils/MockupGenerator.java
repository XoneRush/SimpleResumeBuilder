package com.alex.app.utils;

import com.alex.app.model.Person;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MockupGenerator {
    private Person person;
    private String savePath;
    private PDFont font;
    public MockupGenerator(Person person, String savePath ){
        this.person = person;
        this.savePath = savePath;
    }

    public void MockupOne() throws IOException, ParseException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        font = PDType0Font.load(document, new File("src/main/resources/static/dejavu-sans.book.ttf"));

        //Генерация шаблона
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDRectangle BBox = page.getBBox();

        Rectangle rectangle = new Rectangle(0, 0, (int)(0.25 * BBox.getWidth()),(int)BBox.getHeight());
        drawRect(contentStream,  Color.decode("#4a8bad") , rectangle, true);

        contentStream.beginText();
        //ФИО
        contentStream.newLineAtOffset((int)(BBox.getWidth() * 0.25 + 20), 750);

        showMessage(person.getName() + " " + person.getLast_name(),25, contentStream);
        contentStream.newLine();

        //Личная информация
        showHeader20("ЛИЧНАЯ ИНФОРМАЦИЯ", Color.decode("#4a8bad"), contentStream);
        showMessage("Гражданство: ", 16, contentStream);
        showMessage("Дата рождения: " + dateFormat.format(person.getDate_of_birth()), 16, contentStream);
        showMessage("Образование: ", 16, contentStream);
        showMessage("Пол: ", 16, contentStream);
        showMessage("Семейное положение: ", 16, contentStream);
        contentStream.newLine();

        //Опыт работы
        showHeader20("ОПЫТ РАБОТЫ", Color.decode("#4a8bad"), contentStream);
        //Название компании
        showMessage(" ", 16, contentStream);
        showMessage("Период работы: ", 16, contentStream);
        showMessage("Должность: ", 16, contentStream);
        contentStream.newLine();

        showHeader20("ОБРАЗОВАНИЕ",Color.decode("#4a8bad"), contentStream);
        //Название учеб.заведения
        showMessage(" ", 16, contentStream);
        showMessage("Факультет: ", 16, contentStream);
        showMessage("Специальность: ", 16, contentStream);
        showMessage("Форма обучения:  ", 16, contentStream);
        showMessage("Дата окончания:  ", 16, contentStream);

        contentStream.endText();

        //Конец генерации
        contentStream.close();
        document.save(savePath);
        document.close();
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
    private void showMessage(String message, int fontSize, PDPageContentStream content) throws IOException {
        setFontSizeWithLeading(fontSize, content);
        showTextLn(message, content);
    }
    private void showHeader20(String header, Color color,  PDPageContentStream contentStream) throws IOException {
        setColor(color, contentStream);
        showMessage(header,20, contentStream);
        setColor(Color.BLACK, contentStream);
    }
    private void setFontSizeWithLeading(int fontSize, PDPageContentStream content) throws IOException {
        content.setFont(font, fontSize);
        content.setLeading(fontSize * 1.5f);
    }
    private void showTextLn(String message, PDPageContentStream pdPageContentStream) throws IOException {
        pdPageContentStream.showText(message);
        pdPageContentStream.newLine();
    }
    private void setColor(Color color, PDPageContentStream content) throws IOException {
        content.setStrokingColor(color);
        content.setNonStrokingColor(color);
    }


}
