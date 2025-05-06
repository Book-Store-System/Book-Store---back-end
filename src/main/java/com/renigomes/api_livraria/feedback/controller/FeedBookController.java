package com.renigomes.api_livraria.feedback.controller;

import com.renigomes.api_livraria.feedback.DTO.FeedBackReqDto;
import com.renigomes.api_livraria.feedback.DTO.FeedBackRespDto;
import com.renigomes.api_livraria.feedback.service.FeedBackService;
import com.renigomes.api_livraria.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/feedback")
@Tag(name = "FeedBack")
@SecurityRequirement(name = SecurityConfig.SECURITY)
@AllArgsConstructor
public class FeedBookController {

    private final FeedBackService feedBackService;

    @Operation(
            summary = "Find FeedBack by Book",
            description = "Method responsible for finding feedback by book"
    )
    @GetMapping("/{book_id}")
    public ResponseEntity<List<FeedBackRespDto>> findFeedBackByBook(@PathVariable  Long book_id) {
        return ResponseEntity.ok(feedBackService.findAllByBook(book_id));
    }

    @Operation(
            summary = "Create new feedback",
            description = "Method responsible for creating a new feedback"
    )
    @PostMapping("/{id_user}")
    public ResponseEntity<FeedBackRespDto> createFeedback(@RequestBody @Valid FeedBackReqDto feedBackReqDto,@PathVariable Long id_user) {
        return ResponseEntity.ok(feedBackService.createFeedback(feedBackReqDto, id_user));
    }
}
