package com.example.controller;

import com.example.model.GitRepository;
import com.example.service.GitHubService;
import com.example.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> getRepositories(@PathVariable String owner) throws IOException {
        return gitHubService.getRepositories(owner);
    }

    @PostMapping("/git/publish")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> getAllRepoFromDB(){
        return gitHubService.getRepoFromDB();
    }

    @GetMapping("/searchByKey/{keyword}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> searchRepositories(@PathVariable String keyword) {
        return mongoService.findRepoByKey(keyword);
    }

    @GetMapping("/searchByUser/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> searchRepoByUser(@PathVariable String username){
        return mongoService.findRepoByUser(username);
    }

    @GetMapping("/delete/{owner}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> deleteRepositories(@PathVariable String owner){
        return mongoService.deleteRepoByName(owner);
    }

    @GetMapping("/sortById")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<GitRepository> sortById(){
        return mongoService.sortRepo();
    }
}
