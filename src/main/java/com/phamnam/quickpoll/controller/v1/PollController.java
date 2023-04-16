package com.phamnam.quickpoll.controller.v1;

import com.phamnam.quickpoll.domain.Poll;
import com.phamnam.quickpoll.dto.error.ErrorDetail;
import com.phamnam.quickpoll.exception.ResourceNotFoundException;
import com.phamnam.quickpoll.repository.PollRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController("PollControllerV1")
@Api(value = "Polls",tags = "Poll API" )
@RequestMapping("/v1")
public class PollController {
    @Inject
    private PollRepository pollRepository;
    @ApiOperation(value = "Retrieves all the polls", response = Poll.class
    ,responseContainer = "List")
    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls(){
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }
    @ApiOperation(value = "Create a new poll",
            notes = "The newly created poll Id will be sent in the location response header",
            response = void.class
          )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Poll created successfully",response = void.class),
            @ApiResponse(code = 500, message = "Error creating poll",response = ErrorDetail.class)
    })
    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@Valid @RequestBody Poll poll){
        poll = pollRepository.save(poll);
        System.out.println(poll.getQuestion());
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);

        return new ResponseEntity<>(null,responseHeaders,HttpStatus.CREATED);
    }
    protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException{
        Optional<Poll> poll = pollRepository.findById(pollId);
        if(!poll.isPresent()){
            throw new ResourceNotFoundException("Pool with id:" + pollId + " not found");
        }
        return poll.get();

    }
    @ApiOperation(value = "Retrieves a poll associated with the pollId", response = Poll.class)
    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId){
        return new ResponseEntity<>(verifyPoll(pollId),HttpStatus.OK);
    }
    @ApiOperation(value = "Update a Poll with the pollId", response = void.class)
    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@Valid @RequestBody Poll newPoll, @PathVariable Long pollId){
        verifyPoll(pollId);
        pollRepository.save(newPoll);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @ApiOperation(value = "Delete a Poll with the pollId", response = void.class)
    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        verifyPoll(pollId);
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
}

}
