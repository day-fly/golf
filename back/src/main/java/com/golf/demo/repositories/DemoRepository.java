package com.golf.demo.repositories;

import com.golf.demo.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<Demo, Long> {

    Demo findByName(String name);

}
