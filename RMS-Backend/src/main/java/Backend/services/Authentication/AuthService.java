package Backend.services.Authentication;

import Backend.dtos.SignupRequestDTO;
import Backend.dtos.UserDto;

public interface AuthService {

    UserDto signupUser(SignupRequestDTO signupRequestDTO);

    UserDto signupAdmin(SignupRequestDTO signupRequestDTO);

    Boolean presentEmail(String email);
}
