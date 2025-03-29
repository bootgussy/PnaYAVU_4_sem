
package com.bootgussy.dancecenterservice.core.service.impl;

import com.bootgussy.dancecenterservice.core.exception.AlreadyExistsException;
import com.bootgussy.dancecenterservice.core.exception.IncorrectDataException;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.ScheduleItem;
import com.bootgussy.dancecenterservice.core.repository.ScheduleItemRepository;
import com.bootgussy.dancecenterservice.core.service.ScheduleItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleItemServiceImpl implements ScheduleItemService {
    private final ScheduleItemRepository scheduleItemRepository;

    @Autowired
    public ScheduleItemServiceImpl(ScheduleItemRepository scheduleItemRepository) {
        this.scheduleItemRepository = scheduleItemRepository;
    }

    @Override
    public ScheduleItem findScheduleItemById(Long id) {
        return scheduleItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule item not found. ID: " + id));
    }

    @Override
    public List<ScheduleItem> findAllScheduleItems() {
        return scheduleItemRepository.findAll();
    }

    @Override
    public ScheduleItem createScheduleItem(ScheduleItem scheduleItem) {
        if (
                scheduleItem.getHall() == null ||
                        scheduleItem.getGroup() == null ||
                        scheduleItem.getDayOfWeek() == null ||
                        scheduleItem.getStartTime() == null ||
                        scheduleItem.getEndTime() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(hallId, groupId, dayOfWeek, startTime, endTime).");
        }

        if (
                !(
                        scheduleItem.getDayOfWeek().equals("Monday") ||
                                scheduleItem.getDayOfWeek().equals("Tuesday") ||
                                scheduleItem.getDayOfWeek().equals("Wednesday") ||
                                scheduleItem.getDayOfWeek().equals("Thursday") ||
                                scheduleItem.getDayOfWeek().equals("Friday") ||
                                scheduleItem.getDayOfWeek().equals("Saturday") ||
                                scheduleItem.getDayOfWeek().equals("Sunday")
                )
        ) {
            throw new IncorrectDataException("Day of week is incorrect. Example: Monday");
        }

        if (!scheduleItemRepository.findByHallAndGroupAndDayOfWeekAndStartTimeAndEndTime(
                scheduleItem.getHall(),
                scheduleItem.getGroup(),
                scheduleItem.getDayOfWeek(),
                scheduleItem.getStartTime(),
                scheduleItem.getEndTime()
        ).isEmpty()) {
            throw new AlreadyExistsException("Schedule item already exists." +
                    " Group: " + scheduleItem.getGroup().getId() +
                    ", Hall: " + scheduleItem.getHall().getId() +
                    ", Day of week: " + scheduleItem.getDayOfWeek() +
                    ", Start time: " + scheduleItem.getStartTime() +
                    ", End time: " + scheduleItem.getEndTime());
        }

        List<ScheduleItem> existingScheduleItems = scheduleItemRepository
                .findByDayOfWeekAndHall(scheduleItem.getDayOfWeek(), scheduleItem.getHall());

        boolean isTimeBusy = existingScheduleItems.stream().anyMatch(currentScheduleItem ->
                (currentScheduleItem.getStartTime().isBefore(scheduleItem.getEndTime()) &&
                        currentScheduleItem.getEndTime().isAfter(scheduleItem.getStartTime()))
        );

        if (isTimeBusy) {
            throw new AlreadyExistsException("This time in this hall is busy." +
                    " Group ID: " + scheduleItem.getGroup().getId() +
                    ", Start time: " + scheduleItem.getStartTime() +
                    ", End time: " + scheduleItem.getEndTime());
        }

        return scheduleItemRepository.save(scheduleItem);
    }

    @Override
    public ScheduleItem updateScheduleItem(ScheduleItem scheduleItem) {
        if (
                scheduleItem.getHall() == null ||
                        scheduleItem.getGroup() == null ||
                        scheduleItem.getDayOfWeek() == null ||
                        scheduleItem.getStartTime() == null ||
                        scheduleItem.getEndTime() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(hallId, groupId, dayOfWeek, startTime, endTime).");
        }

        List<ScheduleItem> existingScheduleItems = scheduleItemRepository
                .findByDayOfWeekAndHall(scheduleItem.getDayOfWeek(), scheduleItem.getHall());

        boolean isTimeBusy = existingScheduleItems.stream().anyMatch(currentScheduleItem ->
                (currentScheduleItem.getStartTime().isBefore(scheduleItem.getEndTime()) &&
                        currentScheduleItem.getEndTime().isAfter(scheduleItem.getStartTime()))
        );

        if (!scheduleItemRepository.findByHallAndGroupAndDayOfWeekAndStartTimeAndEndTime(
                scheduleItem.getHall(),
                scheduleItem.getGroup(),
                scheduleItem.getDayOfWeek(),
                scheduleItem.getStartTime(),
                scheduleItem.getEndTime()
        ).isEmpty()) {
            throw new AlreadyExistsException("Schedule item already exists." +
                    " Group: " + scheduleItem.getGroup().getId() +
                    ", Hall: " + scheduleItem.getHall().getId() +
                    ", Day of week: " + scheduleItem.getDayOfWeek() +
                    ", Start time: " + scheduleItem.getStartTime() +
                    ", End time: " + scheduleItem.getEndTime());
        }

        if (isTimeBusy) {
            throw new AlreadyExistsException("This time in this hall is busy." +
                    " Group ID: " + scheduleItem.getGroup().getId() +
                    ", Start time: " + scheduleItem.getStartTime() +
                    ", End time: " + scheduleItem.getEndTime());
        }

        ScheduleItem updatedScheduleItem;

        if (scheduleItemRepository.findById(scheduleItem.getId()).isPresent()) {
            updatedScheduleItem = scheduleItemRepository.save(scheduleItem);
        } else {
            throw new ResourceNotFoundException("The schedule item does not exist." +
                    " ID: " + scheduleItem.getId() +
                    ", Group: " + scheduleItem.getGroup().getId() +
                    ", Hall: " + scheduleItem.getHall().getId() +
                    ", Day of week: " + scheduleItem.getDayOfWeek() +
                    ", Start time: " + scheduleItem.getStartTime() +
                    ", End time: " + scheduleItem.getEndTime());
        }

        return updatedScheduleItem;
    }

    @Override
    public void deleteScheduleItem(Long id) {
        ScheduleItem scheduleItem = scheduleItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule item not found. ID: " + id));

        scheduleItemRepository.delete(scheduleItem);
    }
}
