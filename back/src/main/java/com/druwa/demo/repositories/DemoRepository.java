package com.druwa.demo.repositories;

import com.druwa.demo.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<Demo, Long> {

    Demo findByName(String name);

}
