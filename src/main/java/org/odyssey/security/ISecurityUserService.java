package org.odyssey.security;

public interface ISecurityUserService {

	String validatePasswordResetToken(long id, String token);

}
