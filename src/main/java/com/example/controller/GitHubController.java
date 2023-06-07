package com.example.controller;

import com.example.model.GitRepository;
import com.example.service.GitHubService;
import com.example.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/repo")
public class GitHubController {

    private final GitHubService gitHubService;
    private final MongoService mongoService;

    @Autowired
    public GitHubController(GitHubService gitHubService, MongoService mongoService) {
        this.gitHubService = gitHubService;
        this.mongoService = mongoService;
    }

    @GetMapping("/public/api")
    public String function(){
        return "Public api";
    }

    // GitHub API call operations
    @GetMapping("/protected/git/{owner}")
    public List<GitRepository> getRepositories(@PathVariable String owner) throws IOException {
        return gitHubService.getRepositories(owner);
    }

    @PostMapping("/protected/git/publish")
    public GitRepository publishRepository(@RequestBody GitRepository request) {
        try {
            return gitHubService.publishRepository(request.getOwner(), request.getName(), request.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Database operations
    @GetMapping("/protected/all")
    public List<GitRepository> getAllRepoFromDB(){
        return gitHubService.getRepoFromDB();
    }

    @GetMapping("/protected/searchByKey/{keyword}")
    public List<GitRepository> searchRepositories(@PathVariable String keyword) {
        return mongoService.findRepoByKey(keyword);
    }

    @GetMapping("/protected/searchByUser/{username}")
    public List<GitRepository> searchRepoByUser(@PathVariable String username){
        return mongoService.findRepoByUser(username);
    }

    @GetMapping("/protected/delete/{owner}")
    public List<GitRepository> deleteRepositories(@PathVariable String owner){
        return mongoService.deleteRepoByName(owner);
    }

    @GetMapping("/protected/sort")
    public List<GitRepository> sortById(){
        return mongoService.sortRepo();
    }
}
