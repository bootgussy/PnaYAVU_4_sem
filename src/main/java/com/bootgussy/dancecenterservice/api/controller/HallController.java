package com.bootgussy.dancecenterservice.api.controller;

import com.bootgussy.dancecenterservice.api.dto.create.HallCreateDto;
import com.bootgussy.dancecenterservice.api.dto.response.HallResponseDto;
import com.bootgussy.dancecenterservice.core.mapper.HallMapper;
import com.bootgussy.dancecenterservice.core.model.Hall;
import com.bootgussy.dancecenterservice.core.service.HallService;
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
@RequestMapping("/api/hall")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;
    private final HallMapper hallMapper;

    @GetMapping("/{id}")
    public HallResponseDto findHallById(@PathVariable Long id) {
        Hall hall = hallService.findHallById(id);
        return hallMapper.toResponseDto(hall);
    }

    @GetMapping
    public List<HallResponseDto> findAllHalls() {
        List<Hall> halls = hallService.findAllHalls();
        return hallMapper.toResponseDtoList(halls);
    }

    @PostMapping
    public HallResponseDto createHall(@RequestBody HallCreateDto createDto) {
        Hall hall = hallMapper.toEntity(createDto);
        Hall createdHall = hallService.createHall(hall);
        return hallMapper.toResponseDto(createdHall);
    }

    @PutMapping("/{id}")
    public HallResponseDto updateHall(@PathVariable Long id, @RequestBody HallCreateDto createDto) {
        Hall hall = hallMapper.toEntity(createDto);
        hall.setId(id);
        Hall updatedHall = hallService.updateHall(hall);
        return hallMapper.toResponseDto(updatedHall);
    }

    @DeleteMapping("/{id}")
    public void deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
    }
}
