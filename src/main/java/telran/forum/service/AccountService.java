package telran.forum.service;

import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.dto.UserUpdateDto;

public interface AccountService {
	
	public UserProfileDto addUser(UserRegisterDto userRegDto, String auth);

	public UserProfileDto deleteUser(String id, String auth);

	public UserProfileDto updateUser(UserUpdateDto userUpdateDto, String auth);

}
