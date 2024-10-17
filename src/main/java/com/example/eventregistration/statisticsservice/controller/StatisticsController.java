package com.example.eventregistration.statisticsservice.controller;

import com.example.eventregistration.statisticsservice.model.Statistics;
import com.example.eventregistration.statisticsservice.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @PostMapping
    public ResponseEntity<Statistics> createStatistics(@RequestBody Statistics statistics) {
        return ResponseEntity.ok(statisticsService.createStatistics(statistics));
    }

    @GetMapping
    public ResponseEntity<List<Statistics>> getAllStatistics() {
        return ResponseEntity.ok(statisticsService.getAllStatistics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Statistics> getStatisticsById(@PathVariable Long id) {
        return statisticsService.getStatisticsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Statistics> updateStatistics(@PathVariable Long id, @RequestBody Statistics updatedStatistics) {
        Statistics updated = statisticsService.updateStatistics(id, updatedStatistics);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistics(@PathVariable Long id) {
        return statisticsService.deleteStatistics(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
