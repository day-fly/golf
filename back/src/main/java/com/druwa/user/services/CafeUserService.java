package com.druwa.user.services;

import com.druwa.user.model.CafeUserResponse;
import com.druwa.user.repositories.CafeUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CafeUserService {

    private final CafeUserRepository cafeUserRepository;

    public CafeUserService(CafeUserRepository cafeUserRepository) {
        this.cafeUserRepository = cafeUserRepository;
    }

    public CafeUserResponse getCafeUser() {
        return new CafeUserResponse(cafeUserRepository.findFirstByOrderByInsertRegUptimeDesc());
    }

    @Transactional
    public void clearCafeUser() {
        cafeUserRepository.deleteAllInBatch();
    }

}
