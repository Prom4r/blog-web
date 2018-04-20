package com.pierceecom.blog.validator;

import org.springframework.http.HttpStatus;

import com.pierceecom.blog.exception.PostException;
import com.pierceecom.blog.model.Post;

import static java.util.Objects.isNull;

public class PostValidator {

    public static void validateUpdate(final Post searchEntityToUpdate) throws PostException {
        if (isNull(searchEntityToUpdate)) {
            throw PostException.create("Post not found", HttpStatus.NOT_FOUND);
        }
    }
}
