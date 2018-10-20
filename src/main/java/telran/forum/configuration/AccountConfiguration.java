package telran.forum.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import telran.forum.domain.UserAccount;

@Configuration
@ManagedResource
public class AccountConfiguration {

	@Value("${exp.value}")
	int expPeriod;

	@ManagedAttribute
	public int getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(int expPeriod) {
		this.expPeriod = expPeriod;
	}

	public AccountUserCredential tokenDecode(String auth) {

		int pos = auth.indexOf(" ");
		String token = auth.substring(pos + 1);
		byte[] decodeBytes;

		try {

			decodeBytes = Base64.getDecoder().decode(token);
			String credential = new String(decodeBytes);
			String[] credentials = credential.split(":");
			return new AccountUserCredential(credentials[0], credentials[1]);

		} catch (Exception e){
			return null;
		}
	}

	public boolean authorization(String auth, UserAccount user){
		AccountUserCredential credentials = tokenDecode(auth);
		return credentials.getLogin().equals(user.getId())
				&& credentials.getPassword().equals(user.getPassword());
	}



}
