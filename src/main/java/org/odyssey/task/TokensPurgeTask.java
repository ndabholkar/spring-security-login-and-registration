package org.odyssey.task;

import java.time.Instant;
import java.util.Date;
import org.odyssey.persistence.dao.PasswordResetTokenRepository;
import org.odyssey.persistence.dao.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokensPurgeTask {

	VerificationTokenRepository tokenRepository;

	PasswordResetTokenRepository passwordTokenRepository;

	@Autowired
	public TokensPurgeTask(VerificationTokenRepository tokenRepository, PasswordResetTokenRepository passwordTokenRepository) {
		this.tokenRepository = tokenRepository;
		this.passwordTokenRepository = passwordTokenRepository;
	}

	@Scheduled(cron = "${purge.cron.expression}")
	public void purgeExpired() {

		Date now = Date.from(Instant.now());

		passwordTokenRepository.deleteAllExpiredSince(now);
		tokenRepository.deleteAllExpiredSince(now);
	}
}
