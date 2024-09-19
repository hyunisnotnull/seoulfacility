package com.office.seoul.facility.review;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.office.seoul.config.UnauthorizedAccessException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReviewService {
	
	private final IReviewDao iReviewDao;
	
	public ReviewService(IReviewDao iReviewDao) {
		this.iReviewDao = iReviewDao;
	}

	public List<ReviewDto> getReviews(String facilityId) {
	    List<ReviewDto> reviews = iReviewDao.selectReviewsByFacilityId(facilityId);
	    if (reviews == null) {
	        return new ArrayList<>();
	    }
	    return reviews;
	}

    public void addReview(String facilityId, String userId, String reviewText, int reviewRating) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setSVCID(facilityId);
        reviewDto.setU_m_id(userId);
        reviewDto.setC_text(reviewText);
        reviewDto.setC_rank(reviewRating);
        
        log.info("Adding review: {}", reviewDto);
        
        iReviewDao.insertReview(reviewDto);
    }
    
    public void updateReview(String reviewId, String userId, String reviewText, int reviewRating) {
        ReviewDto reviewDto = iReviewDao.selectReviewById(reviewId);
        if (reviewDto != null && reviewDto.getU_m_id().equals(userId)) {
            reviewDto.setC_text(reviewText);
            reviewDto.setC_rank(reviewRating);
            iReviewDao.updateReview(reviewDto);
        } else {
            throw new UnauthorizedAccessException("이 리뷰를 수정할 권한이 없습니다.");
        }
    }

    public void deleteReview(String reviewId, String userId) {
        ReviewDto reviewDto = iReviewDao.selectReviewById(reviewId);
        if (reviewDto != null && reviewDto.getU_m_id().equals(userId)) {
            iReviewDao.deleteReview(reviewId);
        } else {
            throw new UnauthorizedAccessException("이 리뷰를 삭제할 권한이 없습니다.");
        }
    }

}
