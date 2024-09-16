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
    private static final String FONT_PATH = "src/main/resources/static/dejavu-sans.book.ttf";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final int HEADER1_SIZE = 25;
    private static final int HEADER2_SIZE = 20;
    private static final int TEXT_SIZE = 16;
    private static final float LEADING_MULTIPLIER = 1.5f;
    private static final Color HEADER_COLOR = Color.decode("#4a8bad");
    private Person person;
    private String savePath;
    private PDFont font;
    public MockupGenerator(Person person, String savePath ){
        this.person = person;
        this.savePath = savePath;
    }

    public void generateOne() throws IOException, ParseException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        font = PDType0Font.load(document, new File(FONT_PATH));

        //Генерация шаблона
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDRectangle BBox = page.getBBox();

        Rectangle rectangle = new Rectangle(0, 0, (int)(0.25 * BBox.getWidth()),(int)BBox.getHeight());
        drawRect(contentStream,  HEADER_COLOR, rectangle, true);

        contentStream.beginText();
        //ФИО
        contentStream.newLineAtOffset((int)(BBox.getWidth() * 0.25 + 20), 750);

        showHeader1(person.getName() + " " + person.getLast_name(), HEADER_COLOR ,contentStream);
        contentStream.newLine();

        //Личная информация
        showPersonalInfo(contentStream, dateFormat);
        //Опыт работы
        showExpInfo(contentStream);
        //Образование
        showEducationInfo(contentStream);

        contentStream.endText();

        //Конец генерации
        contentStream.close();
        document.save(savePath);
        document.close();
    }

    private void showPersonalInfo(PDPageContentStream content, SimpleDateFormat dateFormat) throws IOException {
        showHeader2("ЛИЧНАЯ ИНФОРМАЦИЯ", HEADER_COLOR, content);
        showMessage("Гражданство: ", content);
        showMessage("Дата рождения: " + dateFormat.format(person.getDate_of_birth()), content);
        showMessage("Образование: ", content);
        showMessage("Пол: ", content);
        showMessage("Семейное положение: ", content);
        content.newLine();
    }
    private void showExpInfo(PDPageContentStream content) throws IOException {
        showHeader2("ОПЫТ РАБОТЫ", HEADER_COLOR, content);
        //Название компании
        showMessage(" ", content);
        showMessage("Период работы: ", content);
        showMessage("Должность: ", content);
        content.newLine();
    }
    private void showEducationInfo(PDPageContentStream content) throws IOException {
        showHeader2("ОБРАЗОВАНИЕ", HEADER_COLOR, content);
        //Название учеб.заведения
        showMessage(" ",  content);
        showMessage("Факультет: ", content);
        showMessage("Специальность: ",  content);
        showMessage("Форма обучения:  ",  content);
        showMessage("Дата окончания:  ", content);
    }


    private void drawRect(PDPageContentStream content, Color color, Rectangle rect, boolean fill) throws IOException {
        content.addRect(rect.x, rect.y, rect.width, rect.height);
        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }
    private void showMessage(String message, PDPageContentStream content) throws IOException {
        setFontSizeWithLeading(TEXT_SIZE, content);
        showTextLn(message, content);
    }
    private void showHeader1(String header, Color color,  PDPageContentStream content) throws IOException {
        setColor(color, content);
        setFontSizeWithLeading(HEADER1_SIZE, content);
        showTextLn(header, content);
        setColor(Color.BLACK, content);
    }
    private void showHeader2(String header, Color color,  PDPageContentStream content) throws IOException {
        setColor(color, content);
        setFontSizeWithLeading(HEADER2_SIZE, content);
        showTextLn(header, content);
        setColor(Color.BLACK, content);
    }
    private void setFontSizeWithLeading(int fontSize, PDPageContentStream content) throws IOException {
        content.setFont(font, fontSize);
        content.setLeading(fontSize * LEADING_MULTIPLIER);
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
