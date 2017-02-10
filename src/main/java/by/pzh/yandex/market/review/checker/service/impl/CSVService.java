package by.pzh.yandex.market.review.checker.service.impl;

import by.pzh.yandex.market.review.checker.domain.Report;
import by.pzh.yandex.market.review.checker.domain.ReportEntry;
import by.pzh.yandex.market.review.checker.domain.Task;
import by.pzh.yandex.market.review.checker.domain.enums.ReportStatus;
import by.pzh.yandex.market.review.checker.repository.ReportEntryRepository;
import by.pzh.yandex.market.review.checker.repository.ReportRepository;
import by.pzh.yandex.market.review.checker.repository.TaskRepository;
import by.pzh.yandex.market.review.checker.service.constants.ExecutionStatus;
import by.pzh.yandex.market.review.checker.service.dto.CSVTaskDTO;
import by.pzh.yandex.market.review.checker.web.rest.endpoints.resp.ResponseWrapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.fetchEntriesAndStore;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.fetchPoster;
import static by.pzh.yandex.market.review.checker.repository.specifications.TaskSpecifications.filterById;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.jpa.domain.Specifications.where;

/**
 * @author p.zhoidz.
 */
@Service
@Transactional
public class CSVService {

    private TaskRepository taskRepository;
    private ReportRepository reportRepository;
    private ReportEntryRepository reportEntryRepository;

    @Inject
    public CSVService(TaskRepository taskRepository, ReportRepository reportRepository,
                      ReportEntryRepository reportEntryRepository) {
        this.taskRepository = taskRepository;
        this.reportRepository = reportRepository;
        this.reportEntryRepository = reportEntryRepository;
    }

    @Transactional(readOnly = true)
    public CSVTaskDTO generateCSVTask(Long id) throws IOException {
        String[] fileHeader = getTaskHeader();

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator("\n");
        StringBuilder builder = new StringBuilder();

        try (CSVPrinter p = new CSVPrinter(builder, csvFileFormat)) {
            p.printRecord(fileHeader);

            Task task = taskRepository.findOne(where(filterById(id))
                    .and(fetchEntriesAndStore())
                    .and(fetchPoster()));
            AtomicInteger ai = new AtomicInteger(1);

            List<CSVTaskRow> rows = task.getTaskEntries()
                    .stream()
                    .map(taskEntry -> new CSVTaskRow(ai.getAndIncrement(), taskEntry.getStore().getUrl()))
                    .collect(toList());

            p.printRecords(rows);

            return CSVTaskDTO.builder()
                    .content(p.getOut().toString())
                    .poster(task.getPoster())
                    .periodStart(task.getStartDate())
                    .periodEnd(task.getEndDate())
                    .build();
        }

    }

    //TODO make sure batching is working

    /*<property name="hibernate.jdbc.batch_size">100</property>
<property name="hibernate.order_inserts">true</property>
<property name="hibernate.order_updates">true</property>*/
    public ResponseWrapper parseCSVReport(MultipartFile file, Long taskId) {
        try (Reader reader = new InputStreamReader(file.getInputStream())) {

            Task task = taskRepository.getOne(taskId);

            Report report = Report.builder()
                    .task(task)
                    .date(LocalDate.now())
                    .build();

            String[] headers = getTaskHeader();

            List<ReportEntry> entries = new CSVParser(reader,
                    CSVFormat.INFORMIX_UNLOAD_CSV.withHeader(headers))
                    .getRecords()
                    .stream()
                    .skip(1)
                    .map(record ->
                            ReportEntry.builder()
                                    .report(report)
                                    .status(ReportStatus.NOT_VERIFIED)
                                    .text(record.get(headers[2]))
                                    .build())
                    .collect(toList());

            reportRepository.save(report);
            reportEntryRepository.save(entries);
            return new ResponseWrapper(report, ExecutionStatus.SUCCESS);
        } catch (IOException e) {
            return new ResponseWrapper(e, ExecutionStatus.FAILURE);
        }
    }

    private String[] getTaskHeader() {
        return ResourceBundle.getBundle("i18n.csv")
                .getString("csv.task.headers")
                .split(",");
    }

    private static class CSVTaskRow implements Iterable<Object> {
        private Integer number;
        private String storeUrl;

        CSVTaskRow(int number, String storeUrl) {
            this.number = number;
            this.storeUrl = storeUrl;
        }

        @Override
        public Iterator<Object> iterator() {
            return new Iterator<Object>() {
                private List<Object> elements = Arrays.asList(number, storeUrl);
                private int cursor = 0;

                @Override
                public boolean hasNext() {
                    return elements.size() > cursor;
                }

                @Override
                public Object next() {
                    return elements.get(cursor++);
                }
            };
        }
    }
}
