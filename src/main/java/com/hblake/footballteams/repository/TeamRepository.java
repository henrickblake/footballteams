package com.hblake.footballteams.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hblake.footballteams.model.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>{
	
	List<Team> findByName(String name);
	
	List<Team> findAllByOrderByStadiumCapacity();
	
}
