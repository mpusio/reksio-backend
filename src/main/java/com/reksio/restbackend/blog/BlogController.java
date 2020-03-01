package com.reksio.restbackend.blog;

import com.reksio.restbackend.collection.blog.Post;
import com.reksio.restbackend.exception.advertisement.AdvertisementInvalidFieldException;
import com.reksio.restbackend.security.JwtUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation(value = "Create post belonging to user", code = 201)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/user/blog/post")
    public Post addPostToBlogAsUser(@Valid @RequestBody PostSaveRequest postSaveRequest, BindingResult result, HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return blogService.saveNewPost(email, postSaveRequest);
    }

    @ApiOperation(value = "Edit post belonging to user")
    @PutMapping("/user/blog/post")
    public Post editPostAsUser(@Valid @RequestBody PostEditRequest postEditRequest, BindingResult result, HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        handleBindingResult(result);

        return blogService.editPost(email, postEditRequest);
    }

    @ApiOperation(value = "Delete post belonging to user", code = 204)
    @DeleteMapping("/user/blog/post/{postUuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostAsUser(@PathVariable UUID postUuid, HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        blogService.deletePost(email, postUuid);
    }

    @ApiOperation(value = "Get posts belonging to user.")
    @GetMapping("/user/blog/post")
    public List<Post> getPostsAsUser(HttpServletRequest servletRequest){
        String token = servletRequest.getHeader("Authorization");
        String email = JwtUtil.fetchEmail(token);

        return blogService.getUserPosts(email);
    }

    @ApiOperation(value = "Get post")
    @GetMapping("/blog/post/{postUuid}")
    public Post getPost(UUID postUuid){
        return blogService.getPost(postUuid);
    }

    @ApiOperation(value = "Get all posts")
    @GetMapping("/blog/post")
    public List<Post> getPosts(){
        return blogService.getAllPosts();
    }


    private void handleBindingResult(BindingResult result){
        if(result.hasErrors()) {
            throw new AdvertisementInvalidFieldException(
                    result
                            .getFieldErrors()
                            .stream()
                            .map(f -> f.getField() + ": " + f.getDefaultMessage())
                            .reduce((a, b) -> a + ", " + b)
                            .toString());
        }
    }
}