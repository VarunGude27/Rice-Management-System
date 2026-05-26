package Backend.services.Admin;


import Backend.dtos.RiceDto;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    RiceDto riceDto(RiceDto riceDto);
}
