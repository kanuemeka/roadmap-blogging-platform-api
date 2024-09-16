package com.example.blog.data;

import com.example.blog.domain.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends MongoRepository<BlogPost, String> {

    @Query("{ $or: [{ title: { $regex: '?0', $options: \"si\"} }, { content: { $regex: '?0', $options: \"si\" } }, {catergory: { $regex: '?0', $options: \"si\" } }]}")
    List<BlogPost> findAllByTitleContentOrCategory(String queryTerm);
}
