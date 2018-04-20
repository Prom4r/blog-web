package com.pierceecom.blog.service;

import java.util.List;
import java.util.Optional;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;

public interface PostService {

    Long save(PostDto posts) throws PostException;

    void delete(Long id) throws PostException;

    Long update(PostDto posts) throws PostException;

    Optional<PostDto> findById(Long id) throws PostException;

    List<PostDto> findAll();
}
