package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.fetchEntriesAndStore;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.fetchPoster;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.filterById;
import static org.springframework.data.jpa.domain.Specifications.where;


/**
 * @author p.zhoidz.
 */
@Service
public class ReportGenerationService {

    private TaskRepository taskRepository;

    @Inject
    public ReportGenerationService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    final static BaseFont bf = getBaseFont();


    private static Font catFont = new Font(bf, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(bf, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);


    @Transactional(readOnly = true)
    public byte[] generatePDF(Long taskId) {
        //// TODO: 30.1.17 fetch entries fetch store, fetch poster

        Task task = taskRepository.findOne(where(filterById(taskId))
                .and(fetchPoster())
                .and(fetchEntriesAndStore()));


        taskRepository.findOne(taskId);
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            // addMetaData(document);
            addTitlePage(document);
            addContent(document, Collections.singletonList(task));
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            System.out.println("!!!!!");
            return null;
        }
    }


    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Задачи", catFont));
        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document, List<Task> tasks) throws DocumentException {
        for (Task task : tasks) {
            Anchor anchor = new Anchor("Task # " + task.getId() + ". Poster: " + task.getPoster().getEmail(), catFont);
            anchor.setName(task.getPoster().getEmail());

            // Second parameter is the number of the chapter
            Chapter catPart = new Chapter(new Paragraph(anchor), 1);

            Paragraph tasksPar = new Paragraph("Задачи", subFont);
            addEmptyLine(tasksPar, 1);
            Section tasksSubCatPart = catPart.addSection(tasksPar);
/*        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));*/

            // add a list
            // createList(subCatPart);
        /*    Paragraph paragraph = new Paragraph();
            addEmptyLine(paragraph, 5);
            subCatPart.add(paragraph)*/


            // add a table
            createTable(tasksSubCatPart, task.getTaskEntries());

            Paragraph commentsPar = new Paragraph("Комментарий", subFont);
            Section commentsSubCatPart = catPart.addSection(commentsPar);

            Chunk chunk = new Chunk("Тут должны быть комменты", subFont);
            commentsSubCatPart.add(new Paragraph("This is a very important message"));
            commentsSubCatPart.add(chunk);





       /* // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));*/

            // now add all this to the document
            document.add(catPart);

        }


    }

    private static void createTable(Section subCatPart, List<TaskEntry> taskEntries)
            throws BadElementException {
        PdfPTable table = new PdfPTable(2);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("#"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("URL Магазина"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

   /*     c1 = new PdfPCell(new Phrase("Комментарий"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);*/
        table.setHeaderRows(1);

        for (Integer i = 0; i < taskEntries.size(); i++) {
            table.addCell(i.toString());
            table.addCell(taskEntries.get(i).getStore().getUrl());
            //table.addCell(taskEntries.get(i).);
            /*
            table.addCell("2.1");
            table.addCell("2.2");
            table.addCell("2.3");*/
        }


        subCatPart.add(table);

    }

/*    private static void createList(Section subCatPart) {
        com.itextpdf.text.List list = new com.itextpdf.text.List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }*/

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static BaseFont getBaseFont() {
        try {
            return BaseFont.createFont("/fonts/TNR.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
