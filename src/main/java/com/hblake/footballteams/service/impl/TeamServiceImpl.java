package com.hblake.footballteams.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hblake.footballteams.FootballTeamsApplication;
import com.hblake.footballteams.exceptions.TeamAlreadyExistsException;
import com.hblake.footballteams.exceptions.TeamNotFoundException;
import com.hblake.footballteams.model.Team;
import com.hblake.footballteams.repository.TeamRepository;
import com.hblake.footballteams.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	private static final Logger logger = LoggerFactory.getLogger(FootballTeamsApplication.class);	

	@Autowired
	private TeamRepository teamRepository;
	
	public TeamServiceImpl(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	@Override
	public Team createTeam(Team team) throws TeamAlreadyExistsException {
		logger.info("Creating team: " + team.getName());
		List<Team> teams  = teamRepository.findByName(team.getName());
		if (!teams.isEmpty()) {
			logger.info("Team already exists: " + team.getName());
			throw new TeamAlreadyExistsException();
		}
		teamRepository.save(team);
		logger.debug("Created : " + team);
		return team; 	
	}

	@Override
	public Team getTeamByName(String name) throws TeamNotFoundException {
		logger.info("Searching for " + name);
		List<Team> teams  = teamRepository.findByName(name);
		if (teams.isEmpty()) {
			logger.info("Team not found: " + name);
			throw new TeamNotFoundException();
		}
		logger.debug("Team found:" + teams.get(0));
		return teams.get(0);
	}

	@Override
	public List<Team> listAll() {
		logger.info("Returning details of all teams");
	    return teamRepository.findAll();
	}

	@Override
	public List<Team> listAllByCapacity() {
		logger.info("Returning details of all teams ordered by stadium capacity");
	    return teamRepository.findAllByOrderByStadiumCapacity();
	}
}
