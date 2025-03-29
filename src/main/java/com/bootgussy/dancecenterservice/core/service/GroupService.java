package com.bootgussy.dancecenterservice.core.service;

import com.bootgussy.dancecenterservice.core.model.Group;
import java.util.List;

public interface GroupService {
    Group findGroupById(Long id);

    List<Group> findAllGroups();

    Group createGroup(Group group);

    Group updateGroup(Group group);

    void deleteGroup(Long id);
}
