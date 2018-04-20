package com.pierceecom.blog;

import org.junit.Test;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.helper.BaseConverter;
import com.pierceecom.blog.helper.PostBaseConverter;
import com.pierceecom.blog.model.Post;

import static org.assertj.core.api.Assertions.assertThat;

public class PostBaseConverterTest {

    @Test
    public void post_base_converter_test() {
        //given
        final BaseConverter<Post, PostDto> postBaseConverter = new PostBaseConverter();
        final Post post = Post.builder().id(1L).title("Facebook").content("start-date").build();

        //when
        final PostDto postDto = postBaseConverter.convert(post);

        //then
        assertThat(equalPosts(post, postDto)).isTrue();
    }

    private boolean equalPosts(final Post post, final PostDto postDto) {
        return post.getId().equals(postDto.getId()) && post.getTitle().equals(postDto.getTitle()) && post.getContent()
                .equals(postDto.getContent());
    }
}
