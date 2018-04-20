package com.pierceecom.blog;

import java.util.Arrays;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
@Transactional
public class PostControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate restTemplate;

    @Before
    public void setup() throws PostException {
        restTemplate = new TestRestTemplate();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void get_post_should_be_return_200_test() throws Exception {
        //given
        final Long entityId = 1L;
        final PostDto exceptedDto = PostDto.builder().id(1L).title("Facebook").content("example data").build();

        //when
        final ResponseEntity<PostDto> postsDto = restTemplate.getForEntity(getUrl() + entityId, PostDto.class);

        //then
        assertThat(exceptedDto).isEqualTo(postsDto.getBody());
        assertThat(postsDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_post_should_be_return_200_test() {
        //given
        final PostDto postDto = PostDto.builder().id(2L).title("Update data").content("Update data").build();

        //when
        restTemplate.put(getUrl(), postDto, Long.class);
        final ResponseEntity<PostDto> postDtoAfterUpdate = restTemplate.getForEntity(getUrl() + postDto.getId(), PostDto.class);

        //then
        assertThat(postDtoAfterUpdate.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(postDtoAfterUpdate.getBody()).isEqualTo(postDto);
    }

    @Test
    public void get_post_should_be_return_404() throws Exception {
        //given
        final Long entityId = 100L;

        //when
        final ResponseEntity<PostDto> postsDto = restTemplate.getForEntity(getUrl() + entityId, PostDto.class);

        //then
        assertThat(postsDto.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void save_post_should_be_return_201_test() {
        //given
        final PostDto postDto = PostDto.builder().title("Facebook").content("test-data").build();

        //when
        final ResponseEntity<Long> entityId = restTemplate.postForEntity(getUrl(), postDto, Long.class);

        //then
        assertThat(entityId).isNotNull();
        assertThat(entityId.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void get_all_post_should_be_return_200_and_list_postsDto_test() {
        //given

        //when
        final ResponseEntity<PostDto[]> postsList = restTemplate.getForEntity(getUrl(), PostDto[].class);

        //then
        assertThat(postsList.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void delete_post_should_be_return_201_test() {
        //given
        final Long id = 1L;
        //when
        restTemplate.delete(getUrl() + id);
        final ResponseEntity<PostDto[]> postsList = restTemplate.getForEntity(getUrl(), PostDto[].class);

        //then
        assertThat(Arrays.stream(postsList.getBody()).anyMatch(p -> p.getId().equals(id))).isFalse();
    }

    public String getUrl() {
        return "http://localhost:" + port + "/post/";
    }
}

