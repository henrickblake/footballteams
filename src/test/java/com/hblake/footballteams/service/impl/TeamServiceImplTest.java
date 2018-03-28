package com.hblake.footballteams.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hblake.footballteams.exceptions.TeamAlreadyExistsException;
import com.hblake.footballteams.exceptions.TeamNotFoundException;
import com.hblake.footballteams.model.Team;
import com.hblake.footballteams.repository.TeamRepository;

public class TeamServiceImplTest {

	private TeamRepository repository;
	private TeamServiceImpl service;
	private List<Team> storedTeams;
	
	@Before
	public void prepare() {
		repository = mock(TeamRepository.class);
		service = new TeamServiceImpl(repository);
	}
	
	@Before
	public void storeTeams() {

		storedTeams = new ArrayList<>();
		
		Team team1 = new Team();
		team1.setId(1l);
		team1.setName("Arsenal");
		team1.setCity("London");
		team1.setOwner("Arsenal Holdings PLC");
		team1.setStadiumCapacity(60432);
		team1.setCompetition("FA Cup");
		team1.setNumberOfPlayers(23);
		team1.setDateOfCreation(new Date());
		storedTeams.add(team1);
		
		Team team2 = new Team();
		team2.setId(2l);
		team2.setName("West Ham United");
		team2.setCity("London");
		team2.setOwner("David Sullivan");
		team2.setStadiumCapacity(57000);
		team2.setCompetition("FA Cup");
		team2.setNumberOfPlayers(26);
		team2.setDateOfCreation(new Date());
		storedTeams.add(team2);
		
		Team team3 = new Team();
		team3.setId(3l);
		team3.setName("Crewe Alexandra");
		team3.setCity("Crewe");
		team3.setOwner("The Rowlinson Group");
		team3.setStadiumCapacity(10153);
		team3.setCompetition("FA Cup");
		team3.setNumberOfPlayers(31);
		team3.setDateOfCreation(new Date());
		storedTeams.add(team3);
		
		Team team4 = new Team();
		team4.setId(4l);
		team4.setName("Gainsborough Trinity");
		team4.setCity("Gainsborough");
		team4.setOwner("Richard Kane");
		team4.setStadiumCapacity(4340);
		team4.setCompetition("FA Cup");
		team4.setNumberOfPlayers(21);
		team4.setDateOfCreation(new Date());
		storedTeams.add(team4);
	}

	
	@Test
	public void testCreateTeam() {
		
		Team team = new Team();
		team.setName("Crewe Alexandra");
		
		Team savedTeam = new Team();
		savedTeam.setId(1l);
		savedTeam.setName("Crewe Alexandra");
		
		when(repository.save(team)).thenReturn(savedTeam);

		assertNotNull("Team should not be null", savedTeam);
		assertEquals(1L, savedTeam.getId().longValue());
		assertEquals("Crewe Alexandra", savedTeam.getName());
	}
	
	@Test (expected=TeamAlreadyExistsException.class)
	public void testCreateTeamAlreadyExists() {
		
		Team team = new Team();
		
		when(repository.save(team)).thenThrow(new TeamAlreadyExistsException());
		
		service.createTeam(team);
	}
	
	@Test
	public void testGetTeamByName() {
		
		List<Team> teams = new ArrayList<>();
		Team team1 = storedTeams.get(2);
		teams.add(team1);
		
		when(repository.findByName("Crewe Alexandra")).thenReturn(teams);
		
		Team team = service.getTeamByName("Crewe Alexandra");
		assertNotNull("Team should not be null", team);
		assertEquals("Crewe Alexandra", team.getName());
	}
	
	@Test (expected=TeamNotFoundException.class)
	public void testGetTeamByNameNotFound() {
		List<Team> teams = new ArrayList<>();
				
		when(repository.findByName("Luton Town")).thenReturn(teams);
		
		service.getTeamByName("Luton Town");
	}
	
	@Test
	public void testListAll() {

		List<Team> teams = new ArrayList<>();
		for (Team team : storedTeams) {
			teams.add(team);
		}
				
		when(repository.findAll()).thenReturn(teams);
		
		List<Team> allTeams = service.listAll();
		assertEquals(allTeams.size(), 4);
		
	}
	
	@Test
	public void testListAllByCapacity() {

		List<Team> teams = new ArrayList<>();
		for (int i = storedTeams.size() - 1; i >= 0; i--) {
		    teams.add(storedTeams.get(i));
		}
				
		when(repository.findAllByOrderByStadiumCapacity()).thenReturn(teams);
		
		List<Team> allTeams = service.listAllByCapacity();
		assertEquals(allTeams.size(), 4);
		int prevCapacity = 0;
		for (Team team : allTeams) {
			assertTrue(team.getStadiumCapacity() > prevCapacity);
		} 
	}
	
}
