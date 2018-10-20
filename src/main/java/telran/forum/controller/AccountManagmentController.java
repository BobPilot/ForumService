package telran.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.dto.UserUpdateDto;
import telran.forum.service.AccountService;
import telran.forum.api.Link;

@RestController
@RequestMapping(Link.ACCOUNT)
public class AccountManagmentController {
	@Autowired
	AccountService accountService;
	
	@PostMapping(Link.REGISTER)
	public UserProfileDto register(@RequestBody UserRegisterDto userRegisterDto,
			@RequestHeader(value = "Authorization") String auth) {
		return accountService.addUser(userRegisterDto, auth);
	}

	@PutMapping("/{id}")
	public UserProfileDto update(@RequestBody UserUpdateDto userUpdateDto,
								 	@RequestHeader(value = "Authorization") String auth,
								 @PathVariable String id){
		return accountService.updateUser(userUpdateDto, auth);
	}

	@DeleteMapping("/{id}")
	public UserProfileDto delete(@PathVariable String id,
								@RequestHeader(value = "Authorization") String auth){
		return accountService.deleteUser(id, auth);
	}

}
