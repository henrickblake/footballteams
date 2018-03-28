package com.hblake.footballteams.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.hblake.footballteams.exceptions.TeamAlreadyExistsException;
import com.hblake.footballteams.exceptions.TeamNotFoundException;
import com.hblake.footballteams.model.Team;

public interface TeamService {

	public Team createTeam(@RequestBody Team team) throws TeamAlreadyExistsException;
	
	public Team getTeamByName(@PathVariable(value = "name") String name) throws TeamNotFoundException; 
	
	public List<Team> listAll();
	
	public List<Team> listAllByCapacity();		
	
}
