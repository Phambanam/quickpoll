package com.phamnam.quickpoll.client;

import com.phamnam.quickpoll.domain.Option;
import com.phamnam.quickpoll.domain.Poll;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class QuickPollClient {
    private static final String QUICK_POLL_URI_V1 = "http://localhost:8080/v1/polls";
    private final RestTemplate restTemplate = new RestTemplate();
    public Poll getPollById(Long pollId) {
        return restTemplate.getForObject(QUICK_POLL_URI_V1 + "/{pollId}", Poll.class, pollId);
    }

    public List<Poll> getAllPolls(){
        ParameterizedTypeReference<List<Poll>> responseType = new ParameterizedTypeReference<List<Poll>>() {};
        ResponseEntity<List<Poll>> responseEntity = restTemplate.exchange(QUICK_POLL_URI_V1,
                HttpMethod.GET,null,responseType);
        return responseEntity.getBody();
    }
    public URI createPoll(Poll poll){
        return  restTemplate.postForLocation(QUICK_POLL_URI_V1,poll);
    }
    public void updatePoll(Poll poll) {
        restTemplate.put(QUICK_POLL_URI_V1 + "/{pollId}",  poll, poll.getId());
    }

    public void deletePoll(Long pollId) {
        restTemplate.delete(QUICK_POLL_URI_V1 + "/{pollId}",  pollId);
    }

    public static void main(String[] args) {
        QuickPollClient client = new QuickPollClient();



        // Test Create Poll
        Poll newPoll = new Poll();
        newPoll.setQuestion("What is your favourate color?");
        Set<Option> options = new HashSet<>();
        newPoll.setOptions(options);

        Option option1 = new Option();
        option1.setValue("Red");
        options.add(option1);

        Option option2 = new Option();
        option2.setValue("Blue");
        options.add(option2);
        System.out.println(newPoll.getOptions().size());
        URI pollLocation = client.createPoll(newPoll);
        System.out.println("Newly Created Poll Location " + pollLocation);
        // Test GetPoll
        Poll poll = client.getPollById(1L);
        System.out.println(poll);

        // Test GetAllPolls
        List<Poll> allPolls = client.getAllPolls();

        int size = allPolls.size();
        System.out.println(allPolls.size());
        // Test Update Poll with Id 6
        Poll pollForId6 = client.getPollById(1L);
        // Add a new Option
        Option newOption = new Option();
        newOption.setValue("The Incredibles");
        pollForId6.getOptions().add(newOption);

        client.updatePoll(pollForId6);

        pollForId6 = client.getPollById(1L);
        System.out.println("Updated poll has " + pollForId6.getOptions().size() + " options");

        // Test Delete
//        client.deletePoll(1L);
    }
}
