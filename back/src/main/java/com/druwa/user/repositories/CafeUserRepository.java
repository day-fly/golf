package com.druwa.user.repositories;

import com.druwa.user.entity.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeUserRepository extends JpaRepository<CafeUser, Long> {

    CafeUser findFirstByOrderByInsertRegUptimeDesc();

}
