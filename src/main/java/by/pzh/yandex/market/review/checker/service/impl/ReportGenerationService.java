package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.TaskEntry;
import by.pzh.yandex.market.review.checker.domain.enums.TaskEntryStatus;
import by.pzh.yandex.market.review.checker.repository.TaskEntryRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.endDateBetween;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.filterForClient;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskEntrySpecifications.statusEquals;
import static org.springframework.data.jpa.domain.Specifications.where;


/**
 * @author p.zhoidz.
 */
@Service
public class ReportGenerationService {

    private TaskEntryRepository taskEntryRepository;

    @Inject
    public ReportGenerationService(TaskEntryRepository taskEntryRepository) {
        this.taskEntryRepository = taskEntryRepository;
    }

    final static BaseFont bf = getBaseFont();


    private static Font catFont = new Font(bf, 18,
            Font.BOLD);
    private static Font redFont = new Font(bf, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(bf, 16, Font.BOLD);
    private static Font small = new Font(bf, 10);

    @Transactional(readOnly = true)
    public byte[] generatePDF(Long clientId, LocalDate start, LocalDate end) {
        List<TaskEntry> entries = taskEntryRepository.findAll(where(endDateBetween(start, end))
                .and(filterForClient(clientId))
                .and(statusEquals(TaskEntryStatus.CONFIRMED, TaskEntryStatus.MANUALLY_CONFIRMED)));

        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();
            addContent(document, entries);
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void addContent(Document document, List<TaskEntry> entries) throws DocumentException {
        Paragraph tasksPar = new Paragraph("Tasks-Задачи", subFont);
        addEmptyLine(tasksPar, 1);
        createTable(tasksPar, entries);
        document.add(tasksPar);
    }

    private static void createTable(Paragraph subCatPart, List<TaskEntry> taskEntries)
            throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(new float[]{30, 100, 400});
        table.setLockedWidth(true);

        PdfPCell c1 = new PdfPCell(new Phrase("#", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("URL Магазина", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Отзыв", catFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (Integer i = 0; i < taskEntries.size(); i++) {
            table.addCell(new Phrase(i.toString(), small));
            table.addCell(new Phrase(taskEntries.get(i).getStore().getUrl(), small));
            table.addCell(new Phrase(taskEntries.get(i).getText(), small));
        }

        subCatPart.add(table);
    }

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
