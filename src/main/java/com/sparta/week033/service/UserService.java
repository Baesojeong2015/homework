package com.sparta.week033.service;

import com.sparta.week033.dto.SignupRequestDto;
import com.sparta.week033.model.User;
import com.sparta.week033.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        String usernameCheck = "^[a-zA-Z0-9]{3,}$";
        boolean usernameResult = Pattern.matches(usernameCheck, username);
        if(!usernameResult){
            throw new IllegalArgumentException("닉네임을 최소 3자 이상, 알파벳 대소문자 및 숫자로 설정해주세요.");
        }

        //패스워드
        String passwordNumber = "^[a-zA-Z0-9]{4,}$";


        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        if (!password.equals(passwordCheck)){
            throw new IllegalArgumentException("패스워드가 다릅니다.");
        }

        if (password.contains(username)){
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어있습니다.");
        }

        boolean passwordResult = Pattern.matches(passwordNumber, password);
        if(!passwordResult){
            throw new IllegalArgumentException("비밀번호를 최소 4자 이상으로 설정해주세요.");
        }


        //패스워드 암호화
        password = passwordEncoder.encode(requestDto.getPassword());



        User user = new User(username, password);
        userRepository.save(user);
    }
}