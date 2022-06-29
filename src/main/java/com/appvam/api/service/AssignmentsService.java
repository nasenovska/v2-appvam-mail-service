package com.appvam.api.service;

import com.appvam.api.models.Assignment;

import java.util.List;

public interface AssignmentsService {

    Assignment get(Long id);

    List<Assignment> getAll(String receiver);

    void send(Assignment assignment);

    void delete(Long id);
}
