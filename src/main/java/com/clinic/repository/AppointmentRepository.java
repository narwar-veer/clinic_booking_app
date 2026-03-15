package com.clinic.repository;

import com.clinic.entity.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {

    @Override
    @EntityGraph(attributePaths = {"patient", "slot", "slot.clinic", "slot.clinic.doctor"})
    java.util.Optional<Appointment> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"patient", "slot", "slot.clinic", "slot.clinic.doctor"})
    Page<Appointment> findAll(Specification<Appointment> specification, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"patient", "slot", "slot.clinic", "slot.clinic.doctor"})
    List<Appointment> findAll(Specification<Appointment> specification, Sort sort);
}
