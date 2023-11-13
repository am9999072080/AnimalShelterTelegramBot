package Telegram.Bot.AnimalShelter.service.impl;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.exception.EntryAlreadyExistsException;
import Telegram.Bot.AnimalShelter.repository.ReportRepository;
import Telegram.Bot.AnimalShelter.service.AdaptationService;
import Telegram.Bot.AnimalShelter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final AdaptationService adaptationService;
    @Override
    public Report create(Report report) {
        return reportRepository.save(report);
    }

    /**
     * Метод разделяет информацию из отчета хозяина животного на разные части
     * @param info Информация которую прислал оунер
     * @return Список для создания отчета
     */
    private List<String> separateInfoFromReport(String info) {
        Pattern pattern = Pattern.compile("Ration: ([\\w\\s]+);\\s\\nOverall health: ([\\w\\s]+);\\s\\nBehavior Change: ([\\w\\s]+);");
        if (info == null || info.isBlank()) {
            throw new IllegalArgumentException("There is no report. Please, try again and put more information");
        }
        Matcher matcher = pattern.matcher(info);
        if (matcher.find()) {        //The find method scans the input sequence looking for the next subsequence that matches the pattern.
            return new ArrayList<>(List.of(matcher.group(0), matcher.group(1), matcher.group(2)));
        } else {
            throw new IllegalArgumentException("Invalid data. Please, check if data is correct and try to send report once more");
        }
    }
    @Override
    public Report createWithTelegramData(String photoId, String info, Long id) {
        Adaptation adaptation = adaptationService.getAllByOwnerId(id).stream()
                .filter(adaptation1 -> adaptation1.getResult().equals(Adaptation.Result.IN_PROGRESS))
                .findFirst().get();
        if (adaptation.getDateOfLastReport().isEqual(LocalDate.now())) {
            throw new EntryAlreadyExistsException("You've already sent the report today");
        }
        List<String> separatedGroups = separateInfoFromReport(info);
        Report report = create(new Report
                (
                adaptation.getId(),
                LocalDate.now(),
                photoId,
                separatedGroups.get(0),
                separatedGroups.get(1),
                separatedGroups.get(2)
                ));
        adaptation.setDateOfLastReport(LocalDate.now());
        adaptationService.update(adaptation);
        return report;
    }

    @Override
    public Report update(Report report) {
        Report thisReport = getById(report.getId());
        ObjectUpdater.oldToNew(report, thisReport);
        return reportRepository.save(thisReport);
    }

    @Override
    public Report getById(Long id) {
        Optional<Report> optionalReport = reportRepository.findById(id);
        if (optionalReport.isEmpty()) {
            throw new NotFoundException("Report is not found");
        }
        return optionalReport.get();    }

    @Override
    public List<Report> getByAdaptationPeriodId(Long id) {
        List<Report> listReport = reportRepository.findByAdaptationPeriodId(id);
        if (listReport.isEmpty()) {
            throw new NotFoundException("Report is not found");
        }
        return listReport;

    }

    @Override
    public Report getByDateAndAdaptationPeriodId(LocalDate date, Long id) {
        Optional<Report> optionalReport = reportRepository.findByReceiveDateAndAdaptationPeriodId(date, id);
        if (optionalReport.isEmpty()) {
            throw new NotFoundException("Report is not found");
        }
        return optionalReport.get();
    }

    @Override
    public List<Report> getAll() {
        List<Report> reports = reportRepository.findAll();
        if (reports.isEmpty()) {
            throw new NotFoundException("Report is not found");
        }
        return reports;

    }

    @Override
    public void delete(Report report) {
        reportRepository.delete(getById(report.getId()));
    }

    @Override
    public void deleteById(Long id) {
        reportRepository.deleteById(getById(id).getId());
    }
}
