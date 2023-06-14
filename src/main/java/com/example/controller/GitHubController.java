package com.example.controller;

import com.example.model.GitRepository;
import com.example.service.GitHubService;
import com.example.service.MongoService;
import model.GitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/repo")
public class GitHubController {

    private final GitHubService gitHubService;
    private final MongoService mongoService;

    @Autowired
    public GitHubController(GitHubService gitHubService, MongoService mongoService) {
        this.gitHubService = gitHubService;
        this.mongoService = mongoService;
    }

    // GitHub API call operations
    @GetMapping("/git/{owner}")
    public List<GitRepo> getRepositories(@PathVariable String owner) throws IOException {
        return gitHubService.getRepositories(owner);
    }

    @PostMapping("/git/publish")
    public GitRepository publishRepository(@RequestBody GitRepository request) {
        try {
            return gitHubService.publishRepository(request.getOwner(), request.getName(), request.getDescription());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Database operations
    @GetMapping("/all")
    public List<GitRepository> getAllRepoFromDB(){
        return gitHubService.getRepoFromDB();
    }

    @GetMapping("/searchByKey/{keyword}")
    public List<GitRepository> searchRepositories(@PathVariable String keyword) {
        return mongoService.findRepoByKey(keyword);
    }

    @GetMapping("/searchByUser/{username}")
    public List<GitRepository> searchRepoByUser(@PathVariable String username){
        return mongoService.findRepoByUser(username);
    }

    @GetMapping("/delete/{owner}")
    public List<GitRepository> deleteRepositories(@PathVariable String owner){
        return mongoService.deleteRepoByName(owner);
    }

    @GetMapping("/sortById")
    public List<GitRepository> sortById(){
        return mongoService.sortRepo();
    }
}
