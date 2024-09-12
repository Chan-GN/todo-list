package org.example.todolist.repository;

import org.example.todolist.domain.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    @Query("SELECT t " +
            "FROM ToDo t " +
            "WHERE t.member.id = :memberId " +
            "ORDER BY t.done ASC, t.id DESC")
    List<ToDo> findAllSorted(@Param("memberId") Long memberId);

}
