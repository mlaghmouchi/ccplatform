package com.sqli.ccplatform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sqli.ccplatform.domain.entity.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    /**
     * Find operators by availability status
     */
    @Query("SELECT o FROM Operator o WHERE o.availability = :availability")
    List<Operator> findByAvailability(@Param("availability") boolean availability);

    /**
     * Find operator by username
     */
    Optional<Operator> findByUsername(String username);

    /**
     * Find operator by email
     */
    Optional<Operator> findByEmail(String email);

    /**
     * Find operators with specific skills
     */
    @Query("SELECT DISTINCT o FROM Operator o JOIN o.skills s WHERE s IN :skills")
    List<Operator> findBySkillsContaining(@Param("skills") List<String> skills);

    /**
     * Find available operators with specific skills
     */
    @Query("SELECT DISTINCT o FROM Operator o JOIN o.skills s WHERE s IN :skills AND o.availability = true")
    List<Operator> findAvailabilityOperatorsBySkills(@Param("skills") List<String> skills);

    /**
     * Count available operators
     */
    long countByAvailabilityTrue();

    /**
     * Find operators with task count less than specified limit
     */
    @Query("SELECT o FROM Operator o WHERE o.availability = true AND SIZE(o.assignedTasks) < :maxTasks")
    List<Operator> findAvailabilityOperatorsWithTasksLessThan(@Param("maxTasks") int maxTasks);
}