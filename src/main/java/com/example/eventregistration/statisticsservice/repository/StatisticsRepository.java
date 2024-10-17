package com.example.eventregistration.statisticsservice.repository;

import com.example.eventregistration.statisticsservice.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
}
