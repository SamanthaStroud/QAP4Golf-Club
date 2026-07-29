package com.golfclub.controller;

import com.golfclub.model.Tournament;
import com.golfclub.service.TournamentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
    @PostMapping
    public ResponseEntity<Tournament> addTournament(@Valid @RequestBody Tournament tournament) {
        return
                ResponseEntity.status(HttpStatus.CREATED).body(tournamentService.addTournament(tournament));
    }

    @GetMapping
    public List<Tournament> getAllTournaments() {
        return tournamentService.getAllTournaments();
    };

    @GetMapping("/{id}")
    public Tournament getTournamentById(@PathVariable Long id) {
        return tournamentService.getTournamentById(id);
    };

    @GetMapping("/search/start-date")
    public List<Tournament> searchByStartDate(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso =
                    org.springframework.format.annotation.DateTimeFormat.ISO.DATE) LocalDate startDate) {
        return tournamentService.searchByStartDate(startDate);
    }

    @GetMapping("/search/location")
    public List<Tournament> searchByLocation(@RequestParam String location) {
        return tournamentService.searchByLocation(location);
    }

    @PostMapping("/{tournamentId}/register/{memberId}")
    public ResponseEntity<Tournament> registerMember(@PathVariable Long tournamentId, @PathVariable Long
            memberId) {
        return ResponseEntity.ok(tournamentService.registerMember(tournamentId, memberId));
    }
};