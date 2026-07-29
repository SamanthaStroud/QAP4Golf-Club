package com.golfclub.service;

import com.golfclub.model.Member;
import com.golfclub.model.Tournament;
import com.golfclub.repository.MemberRepository;
import com.golfclub.repository.TournamentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final MemberRepository memberRepository;

    public TournamentService(TournamentRepository tournamentRepository, MemberRepository memberRepository)
    {
        this.tournamentRepository = tournamentRepository;
        this.memberRepository = memberRepository;
    };

    public Tournament addTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    };

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    };

    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tournament not found: " + id));
    };

    public List<Tournament> searchByStartDate(LocalDate startDate) {
        return tournamentRepository.findByStartDate(startDate);
    };

    public List<Tournament> searchByLocation(String location) {
        return tournamentRepository.findByLocationContainingIgnoreCase(location);
    };

    public Tournament registerMember(Long tournamentId, Long memberId) {
        Tournament tournament = getTournamentById(tournamentId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Member not found: "
                        + memberId));

        tournament.getParticipatingMembers().add(member);
        return tournamentRepository.save(tournament);
    };
};
