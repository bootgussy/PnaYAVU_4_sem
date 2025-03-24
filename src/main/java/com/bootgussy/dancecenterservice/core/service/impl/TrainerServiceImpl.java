package com.bootgussy.dancecenterservice.core.service.impl;

import com.bootgussy.dancecenterservice.core.exception.AlreadyExistsException;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import com.bootgussy.dancecenterservice.core.repository.TrainerRepository;
import com.bootgussy.dancecenterservice.core.service.TrainerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {
    private final TrainerRepository trainerRepository;

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Trainer findTrainerById(Long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found. ID: " + id));
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        Trainer savedTrainer;

        if (
                trainer.getName() == null ||
                trainer.getPhoneNumber() == null ||
                trainer.getDanceStyle() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(name, phoneNumber, danceStyle).");
        }

        if (trainerRepository.findByNameAndPhoneNumber(trainer.getName(), trainer.getPhoneNumber())
                .isEmpty()) {
            savedTrainer = trainerRepository.save(trainer);
        } else {
            throw new AlreadyExistsException("Trainer already exists. " +
                    "Name: " + trainer.getName() +
                    ", Phone number: " + trainer.getPhoneNumber() +
                    ", Dance style: " + trainer.getDanceStyle());
        }

        return savedTrainer;
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        Trainer updatedTrainer;

        if (
                trainer.getName() == null ||
                trainer.getPhoneNumber() == null ||
                trainer.getDanceStyle() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(name, phoneNumber, danceStyle).");
        }

        if (!trainerRepository.findByNameAndPhoneNumber(trainer.getName(), trainer.getPhoneNumber())
                .isEmpty()) {
            throw new AlreadyExistsException("Trainer already exists. " +
                    "Name: " + trainer.getName() +
                    ", Phone number: " + trainer.getPhoneNumber() +
                    ", Dance style: " + trainer.getDanceStyle());
        }

        if (trainerRepository.findById(trainer.getId()).isPresent()) {
            updatedTrainer = trainerRepository.save(trainer);
        } else {
            throw new ResourceNotFoundException("The trainer does not exist. " +
                    "ID: " + trainer.getId() +
                    ", Name: " + trainer.getName() +
                    ", Phone number: " + trainer.getPhoneNumber() +
                    ", Dance style: " + trainer.getDanceStyle());
        }

        return updatedTrainer;
    }

    @Override
    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found. ID: " + id));

        trainerRepository.delete(trainer);
    }
}
