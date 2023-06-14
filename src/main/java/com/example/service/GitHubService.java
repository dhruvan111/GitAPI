package com.example.service;

import com.example.model.GitRepository;
import model.GitRepo;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private final String gitHubToken;
    private final MongoTemplate mongoTemplate;
    private final ModelMapper modelMapper;
    protected static final String collection = "repositories";
    protected static final String user = "owner";
    protected static final String description = "description";


    @Autowired
    public GitHubService(@Value("${github.token}") String gitHubToken, MongoTemplate mongoTemplate, ModelMapper modelMapper) {
        this.gitHubToken = gitHubToken;
        this.mongoTemplate = mongoTemplate;
        this.modelMapper = modelMapper;
    }

    public List<GitRepository> getRepoFromDB(){
        List<GitRepository> gitRepository;
        gitRepository = mongoTemplate.findAll(GitRepository.class, collection);
        return gitRepository;
    }
    public List<GHRepository> fetchRepoFromGit(String user) throws IOException {
        GitHub github = GitHub.connectUsingOAuth(gitHubToken);
        List<GHRepository> repositories = new ArrayList<>();

        GHUser ghUser = github.getUser(user);
        for (GHRepository repo:ghUser.listRepositories()){
            repositories.add(repo);
        }
        return repositories;
    }

    public List<GitRepo> parsingResponse(List<GHRepository> repositories) throws IllegalAccessException {
        List<GitRepo> gitRepoList = new ArrayList<>();

        for (GHRepository repo:repositories){
            GitRepo.Builder gitRepoBuilder = GitRepo.newBuilder();
            gitRepoBuilder.setId(repo.getId());

            if (repo.getOwnerName() != null) {
                gitRepoBuilder.setOwner(repo.getOwnerName());
            }

            if (repo.getName() != null) {
                gitRepoBuilder.setName(repo.getName());
            }

            if (repo.getDescription() != null) {
                gitRepoBuilder.setDescription(repo.getDescription());
            }

            GitRepo gitRepo = gitRepoBuilder.build();
            gitRepoList.add(gitRepo);
        }
        return gitRepoList;
    }

    public List<String> getRepositories(String user) throws IOException, IllegalAccessException {
        List<GHRepository> repositories = fetchRepoFromGit(user);
        List<GitRepo> gitRepoList = parsingResponse(repositories);
        System.out.println("Parsing done.");
//        gitRepoList.forEach(mongoTemplate::save);
        List<String> serializedData = new ArrayList<>();
        for (GitRepo repo:gitRepoList){
//            System.out.println(repo);
            String serialized = String.valueOf(repo.toByteString());
            System.out.println(serialized);
            serializedData.add(serialized);
        }
        return serializedData;
    }

    public GitRepository publishRepository(String owner, String repoName, String description) throws IOException {

        GitHub github;
        try {
            github = GitHub.connectUsingOAuth(gitHubToken);
        } catch (IOException e) {
            System.out.println("Failed to connect to GitHub API: " + e.getMessage());
            return null;
        }
        GHCreateRepositoryBuilder builder = github.createRepository(repoName);
        builder.description(description);
        builder.isTemplate(false);

        GHRepository repository;
        try {
            repository = builder.create();
        } catch (IOException e) {
            System.out.println("Failed to create repository: " + e.getMessage());
            return null;
        }
        GitRepository gitRepository = new GitRepository(repository.getOwnerName(), repository.getName(), repository.getDescription());
        gitRepository.setId(repository.getId());
        mongoTemplate.save(gitRepository);
        return gitRepository;
    }
}
