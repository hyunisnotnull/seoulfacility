package com.office.seoul.facility.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.office.seoul.config.UnauthorizedAccessException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/review")
public class ReviewController {

	private final ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// 리뷰 등록
	@PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestParam("facilityId") String facilityId,
								            @RequestParam("userId") String userId,
								            @RequestParam("reviewText") String reviewText,
								            @RequestParam("reviewRating") int reviewRating) {
		
        try {
            
            log.info("Received review data: facilityId={}, userId={}, reviewText={}, reviewRating={}",
                    facilityId, userId, reviewText, reviewRating);

            
            reviewService.addReview(facilityId, userId, reviewText, reviewRating);

            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            log.error("후기 등록 실패: {}", e.getMessage());
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("후기 등록에 실패했습니다.");
        }
    }
	
	// 리뷰 리스트
	@GetMapping("/{facilityId}")
    public ResponseEntity<List<ReviewDto>> getReviews(@PathVariable("facilityId") String facilityId) {
        List<ReviewDto> reviews = reviewService.getReviews(facilityId);
        
        log.info("Retrieved reviews for facilityId {}: {}", facilityId, reviews);

        return ResponseEntity.ok(reviews != null ? reviews : new ArrayList<>());
    }

	// 리뷰 수정
	@PutMapping("/update/{reviewId}")
    public ResponseEntity<String> updateReview(	@PathVariable("reviewId") String reviewId,
									            @RequestParam("userId") String userId,
									            @RequestParam("reviewText") String reviewText,
									            @RequestParam("reviewRating") int reviewRating) {
		
        try {
            reviewService.updateReview(reviewId, userId, reviewText, reviewRating);
            return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
            
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            
        } catch (Exception e) {
        	
            log.error("리뷰 수정 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 수정에 실패했습니다.");
        }
    }

	// 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(	@PathVariable("reviewId") String reviewId,
            									@RequestParam("userId") String userId) {
    	
        try {
            reviewService.deleteReview(reviewId, userId);
            return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
            
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
            
        } catch (Exception e) {
            log.error("리뷰 삭제 실패: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 삭제에 실패했습니다.");
        }
    }
    
}