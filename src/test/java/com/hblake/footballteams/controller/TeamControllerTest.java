package com.hblake.footballteams.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hblake.footballteams.exceptions.TeamAlreadyExistsException;
import com.hblake.footballteams.exceptions.TeamNotFoundException;
import com.hblake.footballteams.model.Team;
import com.hblake.footballteams.service.TeamService;

@RunWith(SpringRunner.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTest {

	@Autowired
    private MockMvc mvc;
	
	@MockBean
	private TeamService service;
	
	private List<Team> storedTeams;
	
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
	public void testCreateTeam() throws Exception {

		Team team = new Team();
		team.setName("Crewe Alexandra");

		ObjectMapper mapper = new ObjectMapper();
		String jsonTeam = mapper.writeValueAsString(team);

		Team savedTeam = new Team();
		savedTeam.setId(1l);
		savedTeam.setName("Crewe Alexandra");

		when(service.createTeam(any(Team.class))).thenReturn(savedTeam);
	  
        mvc.perform(post("/rest/team/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonTeam)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Crewe Alexandra")));
	}

	@Test
	public void testCreateTeamAlreadyExists() throws Exception {

		Team team = new Team();
		team.setName("Crewe Alexandra");

		ObjectMapper mapper = new ObjectMapper();
		String jsonTeam = mapper.writeValueAsString(team);

		when(service.createTeam(any(Team.class))).thenThrow(new TeamAlreadyExistsException());
	  
        mvc.perform(post("/rest/team/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonTeam)
        		.accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
	}

	@Test
	public void testGetTeamByName() throws Exception {

		Team team = storedTeams.get(2);

		when(service.getTeamByName("Crewe Alexandra")).thenReturn(team);
	    
		mvc.perform(get("/rest/team/getByName/Crewe Alexandra")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(3)))
				.andExpect(jsonPath("$.name", is("Crewe Alexandra")))
				.andExpect(jsonPath("$.city", is("Crewe")));
	}

	@Test
	public void testGetTeamByNameNotFound() throws Exception {

		when(service.getTeamByName("Luton Town")).thenThrow(new TeamNotFoundException());
	    
		mvc.perform(get("/rest/team/getByName/Luton Town")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testListAll() throws Exception {
		
		List<Team> teams = new ArrayList<>();
		for (Team team : storedTeams) {
			teams.add(team);
		}
				
		when(service.listAll()).thenReturn(teams);
	    
		mvc.perform(get("/rest/team/listAll")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is("Arsenal")))
				.andExpect(jsonPath("$[0].city", is("London")))
				.andExpect(jsonPath("$[1].id", is(2)))
				.andExpect(jsonPath("$[1].name", is("West Ham United")))
				.andExpect(jsonPath("$[1].city", is("London")))
				.andExpect(jsonPath("$[2].id", is(3)))
				.andExpect(jsonPath("$[2].name", is("Crewe Alexandra")))
				.andExpect(jsonPath("$[2].city", is("Crewe")))
				.andExpect(jsonPath("$[3].id", is(4)))
				.andExpect(jsonPath("$[3].name", is("Gainsborough Trinity")))
				.andExpect(jsonPath("$[3].city", is("Gainsborough")));
	}

	@Test
	public void testListAllByCapacity() throws Exception {

		List<Team> teams = new ArrayList<>();
		for (int i = storedTeams.size() - 1; i >= 0; i--) {
		    teams.add(storedTeams.get(i));
		}
				
		when(service.listAll()).thenReturn(teams);
	    
		mvc.perform(get("/rest/team/listAll")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[0].id", is(4)))
				.andExpect(jsonPath("$[0].name", is("Gainsborough Trinity")))
				.andExpect(jsonPath("$[0].city", is("Gainsborough")))
				.andExpect(jsonPath("$[0].stadiumCapacity", is(4340)))
				.andExpect(jsonPath("$[1].id", is(3)))
				.andExpect(jsonPath("$[1].name", is("Crewe Alexandra")))
				.andExpect(jsonPath("$[1].city", is("Crewe")))
				.andExpect(jsonPath("$[1].stadiumCapacity", is(10153)))
				.andExpect(jsonPath("$[2].id", is(2)))
				.andExpect(jsonPath("$[2].name", is("West Ham United")))
				.andExpect(jsonPath("$[2].city", is("London")))
				.andExpect(jsonPath("$[2].stadiumCapacity", is(57000)))
				.andExpect(jsonPath("$[3].id", is(1)))
				.andExpect(jsonPath("$[3].name", is("Arsenal")))
				.andExpect(jsonPath("$[3].city", is("London")))
				.andExpect(jsonPath("$[3].stadiumCapacity", is(60432)));
	}

}
