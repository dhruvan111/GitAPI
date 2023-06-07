package com.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "repositories")
public class GitRepository {
    @Id
    private long id;
    private String owner;
    private String name;
    private String description;

    public GitRepository() {
        // can be used for serialization
    }

    public GitRepository(String owner, String name, String description) {

        this.owner = owner;
        this.name = name;
        this.description = description;
    }
    public void setId(long id){
        this.id = id;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }
    public String getOwner() {
        return owner;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
