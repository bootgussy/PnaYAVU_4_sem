package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.api.dto.create.ScheduleItemCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.ScheduleItemResponseDto;
import com.bootgussy.dancecenterservice.core.mapper.ScheduleItemMapper;
import com.bootgussy.dancecenterservice.core.model.ScheduleItem;
import com.bootgussy.dancecenterservice.core.service.ScheduleItemService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedule_item")
@RequiredArgsConstructor
public class ScheduleItemController {
    private final ScheduleItemService scheduleItemService;
    private final ScheduleItemMapper scheduleItemMapper;

    @GetMapping("/{id}")
    public ScheduleItemResponseDto findScheduleItemById(@PathVariable Long id) {
        ScheduleItem scheduleItem = scheduleItemService.findScheduleItemById(id);
        return scheduleItemMapper.toResponseDto(scheduleItem);
    }

    @GetMapping
    public List<ScheduleItemResponseDto> findAllScheduleItems() {
        List<ScheduleItem> scheduleItems = scheduleItemService.findAllScheduleItems();
        return scheduleItemMapper.toResponseDtoList(scheduleItems);
    }

    @PostMapping
    public ScheduleItemResponseDto createScheduleItem(@RequestBody ScheduleItemCreateDto createDto) {
        ScheduleItem scheduleItem = scheduleItemMapper.toEntity(createDto);
        ScheduleItem createdScheduleItem = scheduleItemService.createScheduleItem(scheduleItem);
        return scheduleItemMapper.toResponseDto(createdScheduleItem);
    }

    @PutMapping("/{id}")
    public ScheduleItemResponseDto updateScheduleItem(
            @PathVariable Long id,
            @RequestBody ScheduleItemCreateDto createDto
    ) {
        ScheduleItem scheduleItem = scheduleItemMapper.toEntity(createDto);
        scheduleItem.setId(id);
        ScheduleItem updatedScheduleItem = scheduleItemService.updateScheduleItem(scheduleItem);
        return scheduleItemMapper.toResponseDto(updatedScheduleItem);
    }

    @DeleteMapping("/{id}")
    public void deleteScheduleItem(@PathVariable Long id) {
        scheduleItemService.deleteScheduleItem(id);
    }
}