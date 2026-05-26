package Backend.controllers;


import Backend.dtos.RiceDto;
import Backend.entites.Client;
import Backend.entites.Rice;
import Backend.repository.RiceRepository;
import Backend.repository.UserRepository;
import Backend.services.Admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RiceRepository riceRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add-rice")
    public RiceDto addRice(@RequestBody RiceDto riceDto){

        Client owner = userRepository.findByFirstName(riceDto.getOwnerName())
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        Rice rice = new Rice();

        rice.setName(riceDto.getName());
        rice.setPrice(riceDto.getPrice());
        rice.setDescription(riceDto.getDescription());
        rice.setImageUrl(riceDto.getImageUrl());
        rice.setOwner(owner);

        return riceRepository.save(rice).getDto();
    }
}
