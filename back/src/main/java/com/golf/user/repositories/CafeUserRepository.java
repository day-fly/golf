package com.golf.user.repositories;

import com.golf.user.entity.CafeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeUserRepository extends JpaRepository<CafeUser, Long> {

    CafeUser findFirstByOrderByInsertRegUptimeDesc();

}
