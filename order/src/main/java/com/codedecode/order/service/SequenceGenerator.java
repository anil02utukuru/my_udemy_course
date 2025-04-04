package com.codedecode.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.codedecode.order.entity.Sequence;

@Service
public class SequenceGenerator {

    @Autowired
    private MongoOperations mongoOperations;

    public int generateNextOrderId(){
        mongoOperations.findAndModify(
        query(where("_id").is("sequence")),
        new Update().inc("sequence", 1),
        options().returnNew(true).upsert(true),
        Sequence.class);           
    }
}
