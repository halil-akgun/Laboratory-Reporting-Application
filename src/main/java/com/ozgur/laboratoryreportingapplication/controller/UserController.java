package com.ozgur.laboratoryreportingapplication.controller;

import com.ozgur.laboratoryreportingapplication.security.UserDetailsImpl;
import com.ozgur.laboratoryreportingapplication.shared.RegisterRequest;
import com.ozgur.laboratoryreportingapplication.shared.UpdateUserRequest;
import com.ozgur.laboratoryreportingapplication.shared.UserResponse;
import com.ozgur.laboratoryreportingapplication.shared.ResponseMessage;
import com.ozgur.laboratoryreportingapplication.service.UserService;
import com.ozgur.laboratoryreportingapplication.shared.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage<UserResponse> save(@Valid @RequestBody RegisterRequest request) {

        ResponseMessage<UserResponse> response = userService.saveUser(request);
        logger.info("Assistant saved with username " + request.getUsername());
        return response;
    }

    @GetMapping("getAllUsers")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    Page<UserResponse> getAllUsers(Pageable pageable, @CurrentUser UserDetailsImpl userDetails) {
        return userService.getAllUsers(pageable, userDetails);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_LABORATORY_ASSISTANT')")
    UserResponse getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PutMapping("/{username}")
    @PreAuthorize("#username == principal.username")
    ResponseMessage<?> updateUser(@PathVariable String username, @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateUser(username, request);
    }
    /*  if (#username != principal.username)
    AuthEntryPoint sınıfında yetkilendirme hatasını işleyerek özel
    bir hata mesajı dönüyor.Ancak @PreAuthorize("#username == principal.username")
    ifadesi, yetkilendirme hatası durumunda doğrudan AccessDeniedException fırlatarak
    bu hata durumunu ele alıyor ve AuthEntryPoint sınıfına ulaşmadan 403 Forbidden hatası dönüyor.
     */
}
