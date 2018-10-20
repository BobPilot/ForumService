package telran.forum.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.forum.configuration.AccountConfiguration;
import telran.forum.configuration.AccountUserCredential;
import telran.forum.dao.UserAccountRepository;
import telran.forum.domain.UserAccount;
import telran.forum.dto.UserProfileDto;
import telran.forum.dto.UserRegisterDto;
import telran.forum.dto.UserUpdateDto;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	@Override
	public UserProfileDto addUser(UserRegisterDto userRegDto, String auth) {
		AccountUserCredential credentials = accountConfiguration.tokenDecode(auth);
		if (userRepository.existsById(credentials.getLogin())) {
			throw new UserExistException();
		}
		UserAccount userAccount = UserAccount.builder()
				.id(credentials.getLogin())
				.password(credentials.getPassword())
				.firstName(userRegDto.getFirstName())
				.lastName(userRegDto.getLastName())
				.role("User")
				.expDate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
				.build();
		userRepository.save(userAccount);
		return new UserProfileDto(credentials.getLogin(),
				userRegDto.getFirstName(), userRegDto.getLastName());
	}

	@Override
	public UserProfileDto deleteUser(String id, String auth) {

		UserAccount user = findUser(id);
		userRepository.deleteById(id);

		return new UserProfileDto(user.getId(), user.getFirstName(), user.getLastName());
	}

	@Override
	public UserProfileDto updateUser(UserUpdateDto userUpdateDto, String auth) {

		UserAccount user = findUser(userUpdateDto.getId());

		if(checkData(user.getFirstName(), userUpdateDto.getFirstName())){
			user.setFirstName(userUpdateDto.getFirstName());
		}
		if(checkData(user.getLastName(), userUpdateDto.getLastName())){
			user.setLastName(userUpdateDto.getLastName());
		}
		if(checkData(user.getPassword(), userUpdateDto.getPassword())){
			user.setPassword(userUpdateDto.getPassword());
		}
		userRepository.save(user);

		return new UserProfileDto(user.getId(), user.getFirstName(), user.getLastName());
	}

	private boolean checkData(String userData, String updData) {

		return updData != null && !userData.equals(updData);

	}


	private UserAccount findUser(String id){
		return userRepository.findById(id).orElse(null);
	}

}
