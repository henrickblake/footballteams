package com.hblake.footballteams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hblake.footballteams.exceptions.TeamAlreadyExistsException;
import com.hblake.footballteams.exceptions.TeamNotFoundException;
import com.hblake.footballteams.model.Team;
import com.hblake.footballteams.service.TeamService;

@RestController
@RequestMapping("/rest/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}

	// Create a team
	@PostMapping("/create")
	public Team createTeam(@RequestBody Team team) throws TeamAlreadyExistsException {
		Team savedTeam = teamService.createTeam(team);
		return savedTeam;
	}

	// Get a specified team
	@GetMapping("/getByName/{name}")
	public Team getTeamByName(@PathVariable(value = "name") String name) throws TeamNotFoundException {
		return teamService.getTeamByName(name);
	}

	// List all teams
	@GetMapping("/listAll")
	public List<Team> listAll() {
		return teamService.listAll();
	}

	// List all teams by stadium capacity
	@GetMapping("/listAllByCapacity")
	public List<Team> listAllByCapacity() {
		return teamService.listAllByCapacity();
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Team not found") 
	@ExceptionHandler(TeamNotFoundException.class)
	public void teamNotFound() {
	}
	
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Team already exists")
	@ExceptionHandler(TeamAlreadyExistsException.class)
	public void conflict() {
	}
}
