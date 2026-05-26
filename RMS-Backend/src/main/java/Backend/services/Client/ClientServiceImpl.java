package Backend.services.Client;


import Backend.dtos.RiceDto;
import Backend.entites.Rice;
import Backend.repository.RiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private RiceRepository riceRepository;

    @Override
    public List<RiceDto> getAllRice(){

        return riceRepository.findAll()
                .stream()
                .map(Rice::getDto)
                .toList();
    }
}
