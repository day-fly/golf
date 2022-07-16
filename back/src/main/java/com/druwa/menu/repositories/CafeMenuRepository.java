package com.druwa.menu.repositories;

import com.druwa.menu.entity.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CafeMenuRepository extends JpaRepository<CafeMenu, Long> {

    List<CafeMenu> findAllBySaleYn(String saleYn);

}
