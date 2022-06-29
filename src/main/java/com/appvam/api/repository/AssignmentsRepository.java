package com.appvam.api.repository;

import com.appvam.api.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentsRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAssignmentsByReceiver(String receiver);
}
