package com.sprint.mission.discodeit.controller;


import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.dto.request.LoginRequest;
import com.sprint.mission.discodeit.dto.request.UserRoleUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.mapper.UserMapper;
import com.sprint.mission.discodeit.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final UserMapper userMapper;

//  @PostMapping(path = "login")
//  public ResponseEntity<UserDto> login(@Valid @RequestBody LoginRequest loginRequest) {
//    UserDto user = authService.login(loginRequest);
//    return ResponseEntity
//        .status(HttpStatus.OK)
//        .body(user);
//  }

  @GetMapping("/me")
  public UserDto me(Authentication authentication) {
    return userMapper.toDto((User) authentication.getPrincipal());
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    request.getSession(false).invalidate();
    SecurityContextHolder.clearContext();
    response.setStatus(HttpServletResponse.SC_OK);
  }

  private final UserRepository userRepository;
  private final SessionRegistry sessionRegistry;

  @PutMapping("/role")
  @PreAuthorize("hasRole('ADMIN')")
  public UserDto updateRole(@RequestBody UserRoleUpdateRequest request) {
    User user = userRepository.findByUsername(request.username())
        .orElseThrow(NoSuchElementException::new);

    user.setRoles(Set.of(request.role()));
    userRepository.save(user);

    sessionRegistry.getAllPrincipals().stream()
        .filter(p -> p instanceof UserDetails)
        .filter(p -> ((UserDetails) p).getUsername().equals(request.username()))
        .flatMap(p -> sessionRegistry.getAllSessions(p, false).stream())
        .forEach(SessionInformation::expireNow);

    return userMapper.toDto(user);
  }
}
