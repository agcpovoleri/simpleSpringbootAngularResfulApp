package com.example.forumapp.rest;

import com.example.forumapp.config.filter.StatelessAuthenticationFilter;
import com.example.forumapp.dto.DtoConverter;
import com.example.forumapp.dto.UserCreateRequest;
import com.example.forumapp.dto.UserDetailResponse;
import com.example.forumapp.dto.UserUpdateRequest;
import com.example.forumapp.entity.User;
import com.example.forumapp.exception.UserNotFoundException;
import com.example.forumapp.service.UserService;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRest {

	private static final Logger logger = LoggerFactory.getLogger(UserRest.class);

	@Autowired
	private UserService userService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public void save(@Valid @NotNull @RequestBody UserCreateRequest userCreateRequest){
        User userToPersist = DtoConverter.buildUserFromCreateRequest(userCreateRequest);
        userService.createUser(userToPersist);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON)
    public void update(@Valid @RequestBody UserUpdateRequest userUpdateRequest){
        User userToUpdate = DtoConverter.buildUserFromUpdateRequest(userUpdateRequest);
        userService.update(userToUpdate);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@Valid @RequestHeader(value = StatelessAuthenticationFilter.TOKEN_KEY, required = true) String authKeyToken,
                       @PathVariable("id") Long id ) {
        userService.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody UserDetailResponse findById(@PathVariable("id") Long id) {
        final User user = Optional.ofNullable(userService.findOne(id)).orElseThrow(UserNotFoundException::new);
        return DtoConverter.buildUserDetail(user);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public List<UserDetailResponse> findAll() {
        return Lists.transform(userService.findAll(), user -> DtoConverter.buildUserDetail(user));
	}
}
