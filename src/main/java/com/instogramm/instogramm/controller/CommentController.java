package com.instogramm.instogramm.controller;

import com.instogramm.instogramm.dto.CommentDTO;
import com.instogramm.instogramm.entity.Comment;
import com.instogramm.instogramm.facade.CommentFacade;
import com.instogramm.instogramm.payload.response.MessageResponse;
import com.instogramm.instogramm.service.CommentService;
import com.instogramm.instogramm.validators.ResponseErrorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {
    private CommentFacade commentFacade;
    private CommentService commentService;
    private ResponseErrorValidator responseErrorValidator;

    @Autowired
    public CommentController(CommentFacade commentFacade, CommentService commentService, ResponseErrorValidator responseErrorValidator) {
        this.commentFacade = commentFacade;
        this.commentService = commentService;
        this.responseErrorValidator = responseErrorValidator;
    }

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComments(@Valid @RequestBody CommentDTO commentDTO,
                                                 @PathVariable("postId") String postId,
                                                 BindingResult bindingResult,
                                                 Principal principal
                                                 ){
        ResponseEntity<Object> listErrors = responseErrorValidator.mappedValidatorService(bindingResult);
        if (!ObjectUtils.isEmpty(listErrors)) return listErrors;

        Comment comment = commentService.saveComment(Long.parseLong(postId), commentDTO, principal);
        CommentDTO commentCreated = commentFacade.commentToCommentDTO(comment);

        return  new ResponseEntity<>(commentCreated, HttpStatus.OK);
    }

    @PostMapping("/{commentId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("commentId") String commentId){
        commentService.deleteComment(Long.parseLong(commentId));
        return new ResponseEntity<>(new MessageResponse("The comment " + commentId + " were deleted"), HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDTO>> getAllCommentsToPost(@PathVariable("postId") String postId){
        List<CommentDTO> commentDTOList = commentService.getAllCommentsForPost(Long.parseLong(postId))
                .stream()
                .map(commentFacade::commentToCommentDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
}
