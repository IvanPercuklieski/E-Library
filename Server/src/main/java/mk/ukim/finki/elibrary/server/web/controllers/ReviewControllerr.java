package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.ReviewCreateDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.dto.update.UpdateReviewDto;
import mk.ukim.finki.elibrary.server.service.backend.application.ReviewApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/reviews")
public class ReviewControllerr {

    private final ReviewApplicationService reviewService;

    public ReviewControllerr(ReviewApplicationService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewCreateDto dto) {
        reviewService.addReview(dto.bookId(), dto.userId(), dto.text(), dto.rating());
        return ResponseEntity.ok("Review added successfully");
    }


    @GetMapping("/book/{bookId}")
    public List<ReviewDisplayDto> getReviewsForBook(@PathVariable Long bookId) {
        return reviewService.getReviewsForBook(bookId);
    }

    // UPDATE review
   // @PreAuthorize("hasRole('BASIC')")
    @PutMapping("update/{id}")
    public ReviewDisplayDto updateReview(@PathVariable Long id,
                                         @RequestBody UpdateReviewDto dto) {
        return reviewService.updateReview(id, dto);
    }

    // DELETE review
   // @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}


