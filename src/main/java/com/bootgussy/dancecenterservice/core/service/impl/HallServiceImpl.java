package com.bootgussy.dancecenterservice.core.service.impl;

import com.bootgussy.dancecenterservice.core.exception.AlreadyExistsException;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.Hall;
import com.bootgussy.dancecenterservice.core.repository.HallRepository;
import com.bootgussy.dancecenterservice.core.service.HallService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {
    private final HallRepository hallRepository;

    @Autowired
    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public Hall findHallById(Long id) {
        return hallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found. ID: " + id));
    }

    @Override
    public List<Hall> findAllHalls() {
        return hallRepository.findAll();
    }

    @Override
    public Hall createHall(Hall hall) {
        Hall savedHall;

        if (
                hall.getName() == null ||
                hall.getArea() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled (name, area).");
        }

        if (hallRepository.findByName(hall.getName()).isEmpty()) {
            savedHall = hallRepository.save(hall);
        } else {
            throw new AlreadyExistsException("Hall already exists." +
                    " Name: " + hall.getName() +
                    ", Area: " + hall.getArea());
        }

        return savedHall;
    }

    @Override
    public Hall updateHall(Hall hall) {
        Hall updatedHall;

        if (
                hall.getName() == null ||
                hall.getArea() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled (name, area).");
        }

        if (hallRepository.findByName(hall.getName()).isPresent()) {
            throw new AlreadyExistsException("Hall already exists." +
                    " Name: " + hall.getName() +
                    ", Area: " + hall.getArea());
        }

        if (hallRepository.findById(hall.getId()).isPresent()) {
            updatedHall = hallRepository.save(hall);
        } else {
            throw new ResourceNotFoundException("The hall does not exist." +
                    " ID: " + hall.getId() +
                    ", Name: " + hall.getName() +
                    "Area: " + hall.getArea());
        }

        return updatedHall;
    }

    @Override
    public void deleteHall(Long id) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hall not found. ID: " + id));

        hallRepository.delete(hall);
    }
}
