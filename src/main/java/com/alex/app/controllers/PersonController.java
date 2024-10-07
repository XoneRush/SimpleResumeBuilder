package com.alex.app.controllers;

import com.alex.app.model.Person;
import com.alex.app.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Controller
public class PersonController {
    @Autowired
    PersonService personService;

    @GetMapping("/")
    public String hello(){
        return "people/index";
    }

    @GetMapping("/info")
    public String info(){
        return "people/information";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute(new Person());
        return "people/create";
    }

    @PostMapping("/done")
    public String doneCreating(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "people/create";
        }

        System.out.println("Создание резюме " + Instant.now());

        personService.deleteResume();
        personService.savePhoto(person);
        personService.createResume(person);
        personService.deletePhoto(person);
        return "redirect:/download";
    }
    @GetMapping(value="/download")
    public ResponseEntity<byte[]> download() throws IOException {
        byte[] pdf = personService.downloadResume();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
