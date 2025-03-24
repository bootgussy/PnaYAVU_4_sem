package com.bootgussy.dancecenterservice.core.service;

import com.bootgussy.dancecenterservice.core.model.ScheduleItem;
import java.util.List;

public interface ScheduleItemService {
    ScheduleItem findScheduleItemById(Long id);

    List<ScheduleItem> findAllScheduleItems();

    ScheduleItem createScheduleItem(ScheduleItem scheduleItem);

    ScheduleItem updateScheduleItem(ScheduleItem scheduleItem);

    void deleteScheduleItem(Long id);
}
