package com.pierceecom.blog.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pierceecom.blog.dto.PostDto;
import com.pierceecom.blog.exception.PostException;
import com.pierceecom.blog.service.PostService;

@RestController
@RequestMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(final PostService postService) {this.postService = postService;}

    @CrossOrigin("http://localhost:4200")
    @GetMapping(produces = {"application/json", "application/xml"})
    public List<PostDto> getAll() {

        return postService.findAll();
    }

    @CrossOrigin("http://localhost:4200")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public @ResponseBody ResponseEntity<PostDto> get(@PathVariable final Long id) throws PostException {
        final Optional<PostDto> posts = postService.findById(id);

        return posts.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CrossOrigin("http://localhost:4200")
    @PostMapping
    public ResponseEntity<Long> save(@RequestBody final PostDto postDto) throws PostException {
        final Long entityId = postService.save(postDto);
        final URI uri = ServletUriComponentsBuilder //
                .fromCurrentRequest().path("/post") //
                .buildAndExpand(entityId) //
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @CrossOrigin("http://localhost:4200")
    @PutMapping
    public ResponseEntity<Long> update(@RequestBody final PostDto postDto) throws PostException {
        final Long entityId = postService.update(postDto);
        final URI uri = ServletUriComponentsBuilder //
                .fromCurrentRequest().path("/post") //
                .buildAndExpand(entityId) //
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @CrossOrigin("http://localhost:4200")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) throws PostException {
        postService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
