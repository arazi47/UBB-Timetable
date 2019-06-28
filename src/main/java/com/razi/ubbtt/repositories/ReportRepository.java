package com.razi.ubbtt.repositories;

import com.razi.ubbtt.domain.Course;
import com.razi.ubbtt.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reportRepository")
public interface ReportRepository extends JpaRepository<Report, Long> {
}