
package com.bootgussy.dancecenterservice.core.service.impl;

import com.bootgussy.dancecenterservice.core.exception.AlreadyExistsException;
import com.bootgussy.dancecenterservice.core.exception.ResourceNotFoundException;
import com.bootgussy.dancecenterservice.core.model.Group;
import com.bootgussy.dancecenterservice.core.model.Student;
import com.bootgussy.dancecenterservice.core.model.Trainer;
import com.bootgussy.dancecenterservice.core.repository.GroupRepository;
import com.bootgussy.dancecenterservice.core.repository.StudentRepository;
import com.bootgussy.dancecenterservice.core.repository.TrainerRepository;
import com.bootgussy.dancecenterservice.core.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final TrainerRepository trainerRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository,
                            TrainerRepository trainerRepository,
                            StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.trainerRepository = trainerRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Group findGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found. ID: " + id));
    }

    @Override
    public List<Group> findAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group createGroup(Group group) {
        if (group.getDifficulty() == null || group.getTrainer() == null) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(trainerId, difficulty).");
        }

        if (group.getStudents() != null) {
            for (Student student : group.getStudents()) {
                if (!studentRepository.existsById(student.getId())) {
                    throw new ResourceNotFoundException("Incorrect Student ID. Student ID: " +
                            student.getId());
                }
            }
        }

        if (group.getTrainer().getId() != null) {
            Trainer managedTrainer = trainerRepository.findById(group.getTrainer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer not found. ID: " +
                            group.getTrainer().getId()));
            group.setTrainer(managedTrainer);
        }

        if (groupRepository.findByTrainerAndDifficulty(group.getTrainer(), group.getDifficulty()).isEmpty()) {
            return groupRepository.save(group);
        } else {
            throw new AlreadyExistsException("Group already exists. " +
                    "Trainer: " + group.getTrainer().getName() +
                    ", Difficulty: " + group.getDifficulty());
        }
    }

    @Override
    public Group updateGroup(Group group) {
        if (
                group.getDifficulty() == null ||
                        group.getTrainer() == null
        ) {
            throw new ResourceNotFoundException("Incorrect JSON. All fields must be filled " +
                    "(trainerId, difficulty).");
        }

        if (group.getStudents() != null) {
            for (Student student : group.getStudents()) {
                if (!studentRepository.existsById(student.getId())) {
                    throw new ResourceNotFoundException("Incorrect Student ID. Student ID: " +
                            student.getId());
                }
            }
        }

        if (groupRepository.findByTrainerAndDifficulty(
                group.getTrainer(),
                group.getDifficulty()
        ).isPresent()) {
            throw new AlreadyExistsException("Group already exists. " +
                    "TrainerId: " + group.getTrainer().getId() +
                    ", Difficulty: " + group.getDifficulty());
        }

        Group updatedGroup;

        if (groupRepository.findById(group.getId()).isPresent()) {
            updatedGroup = groupRepository.save(group);
        } else {
            throw new ResourceNotFoundException("The group does not exist." +
                    " ID: " + group.getId() +
                    ", Trainer: " + group.getTrainer().getName() +
                    ", Difficulty: " + group.getDifficulty());
        }

        return updatedGroup;
    }

    @Override
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found. ID: " + id));

        groupRepository.delete(group);
    }
}
