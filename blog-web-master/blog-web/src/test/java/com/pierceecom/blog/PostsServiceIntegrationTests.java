package com.pierceecom.blog;

import java.util.Optional;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;
import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
public class PostsServiceIntegrationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private PostService postService;

    @Test
    public void operation_on_database_simple_test() throws PostException {
        //given
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final PostDto dtoToSave = PostDto.builder().title("Instagram").content("test-data").build();

        //when
        final Long postsId = postService.save(dtoToSave);

        //then
        assertThat(postsId).isNotNull();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "post", "id = " + postsId)).isEqualTo(1);

        final Optional<PostDto> searchPosts = postService.findById(postsId);
        assertThat(searchPosts.isPresent()).isTrue();
        final Post entity = Post.builder().id(postsId).title(dtoToSave.getTitle()).content(dtoToSave.getContent()).build();
        searchPosts.ifPresent(p -> {
            assertThat(equalsSearchPostsWithEntity(entity, p)).isTrue();
        });
    }

    private boolean equalsSearchPostsWithEntity(final Post entity, final PostDto modelSearch) {
        return entity.getId().equals(modelSearch.getId()) && entity.getContent().equals(modelSearch.getContent()) && entity.getTitle()
                .equals(modelSearch.getTitle());
    }
}
