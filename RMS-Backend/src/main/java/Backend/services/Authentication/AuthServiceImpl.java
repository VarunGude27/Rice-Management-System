package Backend.services.Authentication;

import Backend.dtos.SignupRequestDTO;
import Backend.dtos.UserDto;
import Backend.entites.Client;
import Backend.enums.UserRole;
import Backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto signupUser(SignupRequestDTO signupRequestDTO){

        if(!signupRequestDTO.getPassword().equals(signupRequestDTO.getConfirmPassword())){

            throw  new IllegalArgumentException("Password do not match");
        }

        Client user = new Client();

        user.setFirstName(signupRequestDTO.getFirstName());
        user.setLastName(signupRequestDTO.getLastName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhoneNumber(signupRequestDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        return userRepository.save(user).getDto();
    }

    @Override
    public UserDto signupAdmin(SignupRequestDTO signupRequestDTO){

        if(!signupRequestDTO.getPassword().equals(signupRequestDTO.getConfirmPassword())){

            throw new IllegalArgumentException("Password do not match");
        }

        Client user = new Client();

        user.setFirstName(signupRequestDTO.getFirstName());
        user.setLastName(signupRequestDTO.getLastName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhoneNumber(signupRequestDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.OWNER);
        return userRepository.save(user).getDto();
    }

    @Override
    public Boolean presentEmail(String email){
        return userRepository.findFirstByEmail(email) != null;
    }

}
