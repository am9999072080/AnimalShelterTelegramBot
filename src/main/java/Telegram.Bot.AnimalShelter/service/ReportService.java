package Telegram.Bot.AnimalShelter.service;

import Telegram.Bot.AnimalShelter.entity.Adaptation;
import Telegram.Bot.AnimalShelter.entity.Report;
import Telegram.Bot.AnimalShelter.exception.EntryAlreadyExistsException;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    /**
     * Save report to DB
     *
     * @param report Report to save in DB
     * @return created report
     */
    Report create(Report report);

    /**
     * Creating report with telegram data
     * {@link AdaptationService#getAllByOwnerId(Long)},
     * {@link AdaptationService#update(Adaptation)}
     * {@link ReportService#create(Report)}
     * methods are used
     *
     * @param photoId Id of photo from chat
     * @param caption Text under the photo
     * @param id Id of an owner
     * @return Report
     * @throws IllegalArgumentException if empty, null or format does not match
     * @throws EntryAlreadyExistsException If report have been already sent today
     */
    Report createWithTelegramData(String photoId, String caption, Long id);

    /**
     * Changing existing report
     *
     * @param report report to change
     * @return Updated report
     */
    Report update(Report report);

    /**
     * Getting report by ID
     *
     * @param id ID of report
     * @return report
     */
    Report getById(Long id);

    /**
     * Getting all reports by adaptation ID
     *
     * @param id Adaptation period ID
     * @return List of reports
     */
    List<Report> getByAdaptationPeriodId(Long id);

    /**
     * Getting reports by date and adaptation ID
     *
     * @param date Report creation date
     * @param id Adaptation ID
     * @return Report
     */
    Report getByDateAndAdaptationPeriodId(LocalDate date, Long id);

    /**
     * Getting list of all reports
     *
     * @return list of reports
     */
    List<Report> getAll();

    /**
     * Deleting reports from DB
     *
     * @param report report to delete
     */
    void delete(Report report);

    /**
     * Deleting reports from DB by ID
     * @param id Report ID
     */
    void deleteById(Long id);


}
