package com.example.reddit.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.reddit.dto.AuthentiticationResponce;
import com.example.reddit.dto.LoginRequest;
import com.example.reddit.dto.RegisterRequest;
import com.example.reddit.exceptions.InvalidTokenException;
import com.example.reddit.exceptions.UserNotFoundException;
import com.example.reddit.modal.NotificationEmail;
import com.example.reddit.modal.User;
import com.example.reddit.modal.VerificationToken;
import com.example.reddit.repository.UserRepository;
import com.example.reddit.repository.VerificationTokenRepository;
import com.example.reddit.security.JWTProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	
	
	private final PasswordEncoder encoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository tokenRepository;
	private final MailService mailSevice;
	private final AuthenticationManager manager;
	private JWTProvider provider;
	
	@Transactional
	public void signUp(RegisterRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setEnabled(false);
		user.setCreated(Instant.now());
		userRepository.save(user);
		
		String token = generateVerificationToken(user);
		mailSevice.sendEmail(new NotificationEmail("Please Activate your account",
				user.getEmail(),"Thank you for signing up, please click on the link to activate your account : "
				+"http:/localhost:8081/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		
		String vToken = UUID.randomUUID().toString();
		VerificationToken token = new VerificationToken();
		token.setUser(user);
		token.setToken(vToken);
		
		tokenRepository.save(token);
		return vToken;
		
	}
	
	@Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new InvalidTokenException("Invalid token"));
		fetchUserAndEnable(verificationToken.get());
		
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username= verificationToken.getUser().getUsername();
		
		User user = userRepository.findByUsername(username).orElseThrow(() ->new UserNotFoundException("User with username " + username + " is not found"));
		user.setEnabled(true);
		userRepository.save(user);
	}

	public AuthentiticationResponce login(LoginRequest request) {
	Authentication auth =	manager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	  String token = provider.generateToken(auth);
	  return new AuthentiticationResponce(token , request.getUsername());
	}

	public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

}
