package com.alex.app.utils;

import com.alex.app.model.Education;
import com.alex.app.model.Person;
import com.alex.app.model.WorkPlace;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class MockupGenerator {
    private static final String FONT_PATH = "src/main/resources/static/dejavu-sans.book.ttf";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final int HEADER1_SIZE = 25;
    private static final int HEADER2_SIZE = 20;
    private static final int HEADER3_SIZE = 12;
    private static final int TEXT_SIZE = 16;
    private static final int TEXT_SIZE2 = 10;
    private static final float LEADING_MULTIPLIER = 1.5f;
    private static final Color HEADER_COLOR = Color.decode("#4a8bad");
    private static final double BACKGROUND_MULTIPLIER = 0.3;
    private static final int MAX_FIRSTPAGE = 3;
    private static final int MAX_NPAGE = 6;

    private Person person;
    private String savePath;
    private PDFont font;
    private PDPageContentStream contentStream;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
    private int counter = 0;

    public MockupGenerator(Person person, String savePath ){
        this.person = person;
        this.savePath = savePath;
    }

    public void generateFirstMockup() throws IOException, ParseException {
        PDDocument document = new PDDocument();
        font = PDType0Font.load(document, new File(FONT_PATH));

        // - 1 page
        PDPage page = new PDPage();


        //Генерация шаблона
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDRectangle BBox = page.getBBox();

        drawBackground(contentStream, BBox);

        //Основная информация
        contentStream.beginText();
        //ФИО
        contentStream.newLineAtOffset((int)(BBox.getWidth() * BACKGROUND_MULTIPLIER + 20), 750);

        showHeader1(person.getName() + " " + person.getLast_name(), HEADER_COLOR ,contentStream);
        contentStream.newLine();

        //Личная информация
        showPersonalInfo(contentStream, dateFormat);

        //Опыт работы
        showExpInfo(MAX_FIRSTPAGE, contentStream);

        //Образование
        showEducationInfo(MAX_FIRSTPAGE,contentStream);

        contentStream.endText();
        //Генерация плашки справа
        contentStream.beginText();
        contentStream.newLineAtOffset(10, 600);

        showContactInfo(contentStream);
        contentStream.newLine();
        showOtherInfo(contentStream);
        contentStream.endText();
        contentStream.close();
        document.addPage(page);

        // - 2 to n page
        int iter;
        while((iter = person.getWorkPlaces().size() + person.getEducations().size()) != 0){
            System.out.println(iter);
            this.counter = 0;
            PDPage n_page = new PDPage();
            contentStream = new PDPageContentStream(document, n_page);

            drawBackground(contentStream, BBox);

            contentStream.beginText();
            contentStream.newLineAtOffset((int)(BBox.getWidth() * BACKGROUND_MULTIPLIER + 20), 750);

            showExpInfo(MAX_NPAGE, contentStream);
            showEducationInfo(MAX_NPAGE, contentStream);


            contentStream.endText();
            contentStream.close();
            document.addPage(n_page);
        }
        //Конец генерации

        document.save(savePath);
        document.close();
    }

    //Background
    private void drawBackground(PDPageContentStream content, PDRectangle BBox) throws IOException {
        Rectangle rectangle = new Rectangle(0, 0, (int)(BACKGROUND_MULTIPLIER * BBox.getWidth()),(int)BBox.getHeight());
        drawRect(content,  HEADER_COLOR, rectangle, true);
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

    //Dynamic information
    private void showPersonalInfo(PDPageContentStream content, SimpleDateFormat dateFormat) throws IOException {
        showHeader2("ЛИЧНАЯ ИНФОРМАЦИЯ", HEADER_COLOR, content);
        showMessage1("Гражданство: ", content);
        showMessage1("Дата рождения: " + dateFormat.format(person.getDate_of_birth()), content);
        showMessage1("Образование: ", content);
        showMessage1("Пол: ", content);
        showMessage1("Семейное положение: ", content);
        content.newLine();
    }
    private void showExpInfo(int max, PDPageContentStream content) throws IOException {
        ArrayList<WorkPlace> workPlaces = (ArrayList<WorkPlace>) person.getWorkPlaces();

        if(!workPlaces.isEmpty()) {
            Iterator<WorkPlace> iterator = workPlaces.iterator();
            while(iterator.hasNext() && this.counter < max){
                WorkPlace workPlace = iterator.next();
                System.out.println(workPlace + " counter: " + counter + " size: " + person.getWorkPlaces().size());

                content.newLine();
                showHeader2("ОПЫТ РАБОТЫ", HEADER_COLOR, content);

                showMessage1(workPlace.getNameOfCompany(), content);
                showMessage1(
                        "Период работы: " + dateFormat.format(workPlace.getStartOfWork()) + " - "
                                + dateFormat.format(workPlace.getEndOfWork()),
                        content
                );
                showMessage1("Должность: " + workPlace.getNameOfRole(), content);
                content.newLine();
                iterator.remove();
                this.counter++;
            }
            person.setWorkPlaces(workPlaces);
        }
    }
    private void showEducationInfo(int max, PDPageContentStream content) throws IOException {
        ArrayList<Education> educations = (ArrayList<Education>) person.getEducations();


        if(!educations.isEmpty()) {
            Iterator<Education> iterator = educations.iterator();
            while(iterator.hasNext() && this.counter < max) {
                Education edu = iterator.next();
                System.out.println(edu  + " counter: " + counter);

                content.newLine();
                showHeader2("ОБРАЗОВАНИЕ", HEADER_COLOR, content);

                showMessage1(edu.getEduName(), content);
                showMessage1("Специальность: " + edu.getMajor(), content);
                showMessage1("Форма обучения:  " + edu.getFormOfEdu(), content);
                showMessage1("Дата окончания:  " + dateFormat.format(edu.getDateOfEnd()), content);
                iterator.remove();
                this.counter++;
            }
            person.setEducations(educations);
        }
    }
    private void showContactInfo(PDPageContentStream content) throws IOException {
        showHeader3("Контактная информация", Color.WHITE, content);
        content.setStrokingColor(Color.WHITE);
        content.setNonStrokingColor(Color.WHITE);
        showMessage2(person.getNumber(), content);
        showMessage2(person.getEmail(), content);
        showMessage2(person.getCity(), content);
    }

    private void showOtherInfo(PDPageContentStream content) throws IOException {
        showHeader3("Желаемая зарплата", Color.WHITE, content);
        setColor(Color.WHITE, content);
        showMessage2(" ", content);
        showHeader3("Занятость", Color.WHITE, content);
        setColor(Color.WHITE, content);
        showMessage2(" ", content);
        showHeader3("Иностранные языки", Color.WHITE, content);
        showMessage2(" ", content);
    }


   //Messages, headers and text
    private void showMessage1(String message, PDPageContentStream content) throws IOException {
        setFontSizeWithLeading(TEXT_SIZE, content);
        showTextLn(message, content);
    }
    private void showMessage2(String message, PDPageContentStream content) throws IOException {
        setFontSizeWithLeading(TEXT_SIZE2, content);
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
    private void showHeader3(String header, Color color,  PDPageContentStream content) throws IOException {
        setColor(color, content);
        setFontSizeWithLeading(HEADER3_SIZE, content);
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
