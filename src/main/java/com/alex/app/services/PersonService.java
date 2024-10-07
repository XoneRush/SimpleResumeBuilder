package com.alex.app.services;

import com.alex.app.model.Person;
import com.alex.app.utils.MockupGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            System.out.println("Выбран шаблон: " + person.getOption());
            MockupGenerator mockupGenerator = new MockupGenerator(person, "src/main/resources/static/doc.pdf");
            switch (person.getOption()){
                case "firstMockup":
                    mockupGenerator.generateFirstMockup();
                    break;
                default:
                    System.out.println("Такого шаблона пока нет");
                    mockupGenerator.generateFirstMockup();
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public void savePhoto(Person person){
        try{
            if(!person.getImageData().isEmpty()) {
                Path filepath = Paths.get("src/main/resources/static/" + person.getImageData().getName() + ".png");
                person.getImageData().transferTo(filepath);
            }
        }catch (IOException e){
            System.out.println("Проблема с фотографией");
        }
    }
    public void deletePhoto(Person person){
        try{
            File file = new File("src/main/resources/static/" + person.getImageData().getName()+ ".png");
            file.delete();
        }catch (Exception e){
            System.out.println("Ошибка при удалении фотографии");
        }
    }
}
