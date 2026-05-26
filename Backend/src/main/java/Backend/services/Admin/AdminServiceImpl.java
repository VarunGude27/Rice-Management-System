package Backend.services.Admin;

import Backend.dtos.RiceDto;
import Backend.entites.Rice;
import Backend.repository.RiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private RiceRepository riceRepository;

    @Override
    public RiceDto riceDto(RiceDto riceDto){

        Rice rice = new Rice();

        rice.setName(riceDto.getName());
        rice.setPrice(riceDto.getPrice());
        rice.setDescription(riceDto.getDescription());
        rice.setImageUrl(riceDto.getImageUrl());
        return riceRepository.save(rice).getDto();
    }
}
