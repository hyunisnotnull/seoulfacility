package com.office.seoul.facility.review;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IReviewDao {
	
	// 리뷰 추가
	int insertReview(ReviewDto reviewDto);

    // 특정 시설에 대한 리뷰 조회
    List<ReviewDto> selectReviewsByFacilityId(@Param("facilityId") String facilityId);
    
    // 리뷰 수정
    int updateReview(ReviewDto reviewDto);

    // 리뷰 삭제
    int deleteReview(String reviewId);

    // 리뷰 조회 (ID로)
    ReviewDto selectReviewById(String reviewId);

}
