package com.pierceecom.blog.helper;

import org.springframework.stereotype.Component;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.model.Post;

@Component
public class PostBaseConverter implements BaseConverter<Post, PostDto> {

    @Override
    public PostDto convert(final Post from) {
        return PostDto.builder() //
                .id(from.getId()) //
                .title(from.getTitle()) //
                .content(from.getContent()) //
                .build();
    }
}
