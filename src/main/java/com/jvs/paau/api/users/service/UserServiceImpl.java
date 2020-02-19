package com.jvs.paau.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jvs.paau.api.users.data.UserEntity;
import com.jvs.paau.api.users.data.UserRepository;
import com.jvs.paau.api.users.shared.UserDto;

@Service
public class UserServiceImpl implements UsersService {

	BCryptPasswordEncoder bCryptPasswordEncoder;
	UserRepository userRepository;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
	   this.userRepository = userRepository;
	   this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		
		//criptografa a senha enviada do usuario 
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		
		
	    userRepository.save(userEntity);
	    
	   // System.out.println(userEntity.getId());
	    UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
	   
		return returnValue;
	}

	
	
}
