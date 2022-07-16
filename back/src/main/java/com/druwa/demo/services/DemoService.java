package com.druwa.demo.services;

import com.druwa.demo.Demo;
import com.druwa.demo.repositories.DemoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private DemoRepository demoRepository;

    @Autowired
    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public long setDemo(String name) {
        Demo demo = new Demo();
        demo.setName(name);
        Demo afterSaveDemo = demoRepository.save(demo);
        System.out.println(">>>>> " + afterSaveDemo.getId());
        return afterSaveDemo.getId();
    }

    public List<Demo> getDemoList() {
        return demoRepository.findAll();
    }

    public Demo getDemoByName(String name) {
        return demoRepository.findByName(name);
    }

}
