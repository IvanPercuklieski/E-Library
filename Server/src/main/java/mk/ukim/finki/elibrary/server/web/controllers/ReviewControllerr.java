package mk.ukim.finki.elibrary.server.web.controllers;

import mk.ukim.finki.elibrary.server.dto.ReviewCreateDto;
import mk.ukim.finki.elibrary.server.dto.ReviewDisplayDto;
import mk.ukim.finki.elibrary.server.service.backend.application.ReviewApplicationService;
import org.springframework.http.ResponseEntity;
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
}


