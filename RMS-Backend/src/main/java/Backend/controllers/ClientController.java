package Backend.controllers;



import Backend.dtos.RiceDto;
import Backend.repository.RiceRepository;
import Backend.services.Client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private RiceRepository riceRepository;

    @GetMapping("/all-rice")
    public List<RiceDto> getAllRice(){

        return  clientService .getAllRice();
    }
}
