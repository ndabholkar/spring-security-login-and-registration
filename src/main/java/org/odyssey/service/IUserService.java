package org.odyssey.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import org.odyssey.persistence.model.PasswordResetToken;
import org.odyssey.persistence.model.User;
import org.odyssey.persistence.model.VerificationToken;
import org.odyssey.web.dto.UserDto;
import org.odyssey.web.error.UserAlreadyExistException;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto) throws UserAlreadyExistException;

	User getUser(String verificationToken);

	void saveRegisteredUser(User user);

	void deleteUser(User user);

	void createVerificationTokenForUser(User user, String token);

	VerificationToken getVerificationToken(String VerificationToken);

	VerificationToken generateNewVerificationToken(String token);

	void createPasswordResetTokenForUser(User user, String token);

	User findUserByEmail(String email);

	PasswordResetToken getPasswordResetToken(String token);

	User getUserByPasswordResetToken(String token);

	Optional<User> getUserByID(long id);

	void changeUserPassword(User user, String password);

	boolean checkIfValidOldPassword(User user, String password);

	String validateVerificationToken(String token);

	String generateQRUrl(User user) throws UnsupportedEncodingException;

	User updateUser2FA(boolean use2FA);

	List<String> getUsersFromSessionRegistry();

}
