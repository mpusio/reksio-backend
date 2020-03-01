package com.reksio.restbackend.blog;

import com.reksio.restbackend.collection.blog.Post;
import com.reksio.restbackend.collection.blog.PostRepository;
import com.reksio.restbackend.exception.post.PostFailedDeleteException;
import com.reksio.restbackend.exception.post.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BlogService {

    private final PostRepository postRepository;

    @Autowired
    public BlogService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post saveNewPost(String email, PostSaveRequest postSaveRequest) {
        Post post = postSaveRequest.convertToPost();
        post.setCreatedBy(email);

        return postRepository.insert(post);
    }

    public Post editPost(String email, PostEditRequest postEditRequest) {
        UUID postUuid = postEditRequest.getPostUuid();
        Post post = postRepository.findByUuidAndCreatedBy(postUuid, email)
                .orElseThrow(() -> new PostNotFoundException("Cannot find post with uuid " + postUuid + " and createdBy " + email));

        post.setTitle(nullChecker(postEditRequest.getTitle(), post.getTitle()));
        post.setContent(nullChecker(postEditRequest.getContent(), post.getContent()));
        post.setTags(nullChecker(postEditRequest.getTags(), post.getTags()));
        post.setEditedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    private <T> T nullChecker(T fieldToInsert, T actualField){
        if (fieldToInsert == null){
            return actualField;
        }
        return fieldToInsert;
    }

    public void deletePost(String email, UUID postUuid) {
        Long deleteResult = postRepository.deleteByUuidAndCreatedBy(postUuid, email);
        if (deleteResult==0L){
            throw new PostFailedDeleteException("Cannot delete post with uuid: " + postUuid + " and created by " + email);
        }
    }

    public Post getPost(UUID postUuid){
        return postRepository.findByUuid(postUuid)
                .orElseThrow(() -> new PostNotFoundException("Cannot find post with uuid " + postUuid ));
    }

    public List<Post> getUserPosts(String email){
        return postRepository.findAllByCreatedBy(email);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByIdDesc();
    }
}
