package com.pierceecom.blog;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;
import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.PostRepository;
import com.pierceecom.blog.service.PostServiceImpl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;
    private PostServiceImpl postService;

    @Before
    public void before() {
        postService = createService();
    }

    @Test
    public void try_save_post_with_null_model_test() {
        final PostDto dto = null;

        assertThatExceptionOfType(PostException.class) //
                .isThrownBy(() -> postService.save(dto)) //
                .withMessage("Post to save is null");
    }

    @Test
    public void try_save_post_with_id_model_test() {
        final PostDto dto = PostDto.builder().id(1L).build();

        assertThatExceptionOfType(PostException.class) //
                .isThrownBy(() -> postService.save(dto)) //
                .withMessage("Save post about id:null failed.");
    }

    @Test
    public void try_update_post_with_null_model_test() {
        final PostDto dto = null;

        assertThatExceptionOfType(PostException.class) //
                .isThrownBy(() -> postService.update(dto)) //
                .withMessage("Post to update is null");
    }

    @Test
    public void try_update_post_with_not_existing_id_model_test() {
        final PostDto dto = PostDto.builder().id(6L).build();

        assertThatExceptionOfType(PostException.class) //
                .isThrownBy(() -> postService.update(dto)) //
                .withMessage("Post not found");
    }

    @Test
    public void try_delete_post_with_not_existing_id_model_test() {
        final PostDto dto = PostDto.builder().id(6L).build();

        assertThatExceptionOfType(PostException.class) //
                .isThrownBy(() -> postService.update(dto)) //
                .withMessage("Post not found");
    }

    private PostServiceImpl createService() {
        final PostRepository repo = Mockito.mock(PostRepository.class);

        when(repo.saveAndFlush(any(Post.class))).thenAnswer(new Answer<Post>() {
            @Override
            public Post answer(final InvocationOnMock invocation) throws Throwable {
                final Post post = (Post) invocation.getArguments()[0];
                post.setId(1L);
                return post;
            }
        });

        when(repo.findById(6L)).thenAnswer(new Answer<Optional<Post>>() {
            @Override
            public Optional<Post> answer(final InvocationOnMock invocation) throws Throwable {
                final Post post = (Post) invocation.getArguments()[0];
                post.setId(6L);
                return Optional.of(post);
            }
        });

        final PostServiceImpl postService = new PostServiceImpl(postRepository, null);
        postService.postRepository = repo;

        return postService;
    }
}
