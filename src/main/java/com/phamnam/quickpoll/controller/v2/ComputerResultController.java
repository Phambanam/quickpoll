package com.phamnam.quickpoll.controller.v2;

import com.phamnam.quickpoll.domain.Vote;
import com.phamnam.quickpoll.dto.OptionCount;
import com.phamnam.quickpoll.dto.VoteResult;
import com.phamnam.quickpoll.repository.VoteRepository;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
@Api(value = "Computer Result Controller",tags = "Compute result API" )
@RestController("ComputerResultControllerV2")
@RequestMapping("/v2")

public class ComputerResultController {
    @Inject
    private VoteRepository voteRepository;

    @GetMapping("/computerResult")
    public ResponseEntity<?> computeResult(@RequestParam Long pollId){
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);
        int totalVotes = 0;
        Map<Long, OptionCount> tempMap = new HashMap<>();
        for (Vote v: allVotes){
            totalVotes++;
            OptionCount optionCount = tempMap.get(v.getOption().getId());
            if (optionCount == null){
                optionCount = new OptionCount();
                optionCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(),optionCount);
            }
            optionCount.setCount(optionCount.getCount()+1);
        }
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());

        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}
