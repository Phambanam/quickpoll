package com.phamnam.quickpoll.controller.v3;

import com.phamnam.quickpoll.domain.Vote;
import com.phamnam.quickpoll.repository.VoteRepository;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
@Api(value = "Vote Controller",tags = "Vote API" )
@RestController("VoteControllerV3")
@RequestMapping("/v3")
public class VoteController {
    @Inject
    private VoteRepository voteRepository;

    @PostMapping("/polls/{pollId}/vote")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote){
        vote = voteRepository.save(vote);
        HttpHeaders responseHeader = new HttpHeaders();
        responseHeader.setLocation(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(vote.getId())
                        .toUri()
        );
        return new ResponseEntity<>(null, responseHeader,HttpStatus.CREATED);
    }
    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId)
    {
        return voteRepository.findByPoll(pollId);
    }

}
