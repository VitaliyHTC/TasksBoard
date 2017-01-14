package com.vitaliyhtc.tasksboard.repositories;

import com.vitaliyhtc.tasksboard.model.BoardVisibility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardVisibilityRepository extends JpaRepository<BoardVisibility, Long> {
}
