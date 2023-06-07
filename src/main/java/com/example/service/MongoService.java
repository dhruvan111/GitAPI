package com.example.service;

import com.example.model.GitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoService {
    MongoTemplate mongoTemplate;
    @Autowired
    MongoService(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<GitRepository> findRepoByKey(String key){
        Query query = new Query();
        query.addCriteria(Criteria.where(GitHubService.description).regex(key, "i"));
        return mongoTemplate.find(query, GitRepository.class);
    }

    public List<GitRepository> findRepoByUser(String owner){
        Query query = new Query();
        query.addCriteria(Criteria.where(GitHubService.user).is(owner));
        return mongoTemplate.find(query, GitRepository.class);
    }

    public List<GitRepository> deleteRepoByName(String userName){
        Query query = new Query();
        query.addCriteria(Criteria.where(GitHubService.user).is(userName));
        return mongoTemplate.findAllAndRemove(query, GitRepository.class);
    }

    public List<GitRepository> sortRepo(){
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        return mongoTemplate.find(query, GitRepository.class);
    }
}
