package com.pierceecom.blog.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;
import com.pierceecom.blog.helper.BaseConverter;
import com.pierceecom.blog.model.Post;
import com.pierceecom.blog.repository.PostRepository;
import com.pierceecom.blog.validator.PostValidator;

import static java.util.Objects.isNull;

@Service
public class PostServiceImpl implements PostService {

    //modifier is public for tests
    public PostRepository postRepository;
    private final BaseConverter<Post, PostDto> postsBaseConverter;

    @Autowired
    public PostServiceImpl(final PostRepository postRepository, final BaseConverter<Post, PostDto> postsBaseConverter) {
        this.postRepository = postRepository;
        this.postsBaseConverter = postsBaseConverter;
    }

    @Override
    public Long save(final PostDto postDto) throws PostException {
        if (isNull(postDto)) {
            throw PostException.create("Post to save is null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final Post post = Post.builder().title(postDto.getTitle()).content(postDto.getContent()).build();

        final Post saveEntity = postRepository.save(post);

        return Optional.ofNullable(saveEntity).map(Post::getId).orElseThrow(
                () -> PostException.create("Save post about id:" + post.getId() + " failed.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public void delete(final Long id) throws PostException {
        final Optional<PostDto> postsDto = findById(id);

        postsDto.map(p -> {
            postRepository.delete(Post.builder().id(p.getId()).title(p.getTitle()).content(p.getContent()).build());
            return true;
        }).orElseThrow(() -> PostException.create("Delete post about id: " + id + " failed.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public Long update(final PostDto postDto) throws PostException {
        if (isNull(postDto)) {
            throw PostException.create("Post to update is null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final Post post = Post.builder().id(postDto.getId()).title(postDto.getTitle()).content(postDto.getContent()).build();
        final Post searchPost = postRepository.findOne(post.getId());

        PostValidator.validateUpdate(searchPost);

        final Post saveEntity = postRepository.save(post);

        return Optional.ofNullable(saveEntity).map(Post::getId).orElseThrow(
                () -> PostException.create("Update post about id:" + post.getId() + " failed.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Override
    public Optional<PostDto> findById(final Long id) throws PostException {
        return Optional.ofNullable(postRepository.findById(id) //
                .map(postsBaseConverter::convert)
                .orElseThrow(() -> PostException.create("Post about id:" + id + " not found.", HttpStatus.NOT_FOUND)));
    }

    @Override
    public List<PostDto> findAll() {
        return postRepository.findAll().stream() //
                .map(postsBaseConverter::convert) //
                .collect(Collectors.toList());
    }
}
