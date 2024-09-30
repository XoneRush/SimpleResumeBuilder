package com.alex.app.services;

import com.alex.app.model.Person;
import com.alex.app.utils.MockupGenerator;
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
           mockupGenerator.generateFirstMockup();
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
