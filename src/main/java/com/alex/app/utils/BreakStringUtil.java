package com.alex.app.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.io.IOException;
import java.util.ArrayList;

public class BreakStringUtil {
    private float margin;
    private PDFont font;
    private float fontSize;
    private float leading;
    private PDPageContentStream contentStream;
    private PDPage page;

    public BreakStringUtil(float margin, PDFont font, float fontSize, PDPageContentStream cs, PDPage page){
        this.margin = margin;
        this.font = font;
        this.fontSize = fontSize;
        this.leading = 1.5f*fontSize;
        this.contentStream = cs;
        this.page = page;
    }

    public void breakString(String line, int startX, int startY) throws IOException {
        FontRenderContext fontRenderContext = new FontRenderContext(null, true, true);
        PDRectangle mediabox = page.getMediaBox();
        float width = mediabox.getWidth() - 2*margin;
        System.out.println(width);
        ArrayList<String> lines = new ArrayList<String>();
        int lastSpace = -1;

        while(line.length() > 0){
            int spaceIndex = line.indexOf(' ', lastSpace+1);
            if (spaceIndex < 0) spaceIndex = line.length();

            String subString = line.substring(0, spaceIndex);
            float size = fontSize * font.getStringWidth(subString)/1000;
            System.out.println(size);
            if(size > width){
                if(lastSpace < 0) lastSpace = spaceIndex;
                subString = line.substring(0, lastSpace);
                lines.add(subString);
                line = line.substring(lastSpace).trim();
                lastSpace=-1;
            } else if (spaceIndex == line.length()) {
                lines.add(line);
                line = "";
            } else{
              lastSpace = spaceIndex;
            }
        }
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(startX, startY);
        for(String str : lines){
            System.out.println(str);
            contentStream.showText(str);
            contentStream.newLineAtOffset(0, -leading);
        }
        contentStream.endText();
    }
}
