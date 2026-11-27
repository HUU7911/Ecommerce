package com.huumod.ecommerce.file.repository;

import com.huumod.ecommerce.file.entity.FileMgmt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileMgmtRepository extends MongoRepository<FileMgmt, String> {
}
