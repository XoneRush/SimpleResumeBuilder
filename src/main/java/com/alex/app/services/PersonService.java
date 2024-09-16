package com.alex.app.services;

import com.alex.app.model.Person;
import com.alex.app.utils.BreakStringUtil;
import com.alex.app.utils.MockupGenerator;
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
import java.text.ParseException;

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
           MockupGenerator mockupGenerator = new MockupGenerator(person,"src/main/resources/static/doc.pdf" );
           mockupGenerator.MockupOne();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
