package com.reksio.restbackend.blog;

import com.reksio.restbackend.collection.blog.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostEditRequest {

    private UUID postUuid;
    @Size(min = 3)
    private String title;
    @Size(min = 10)
    private String content;
    private List<String> tags;

    public Post convertToPost(){
        return Post.builder()
                .uuid(UUID.randomUUID())
                .title(title)
                .content(content)
                .tags(tags)
                .build();
    }
}
