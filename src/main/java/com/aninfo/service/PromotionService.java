package com.aninfo.service;

import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    private static final Double PROMOTION_CANDIDATE_LIMIT = 2000.0;
    private static final Double PROMOTION_REWARD_LIMIT = 500.0;
    private static final Double PROMOTION_REWARD_PERCENTAGE = 0.10;

    public Double applyPromotion(final Double depositAmount) {
        return depositAmount < PROMOTION_CANDIDATE_LIMIT
                ? depositAmount
                : depositAmount + getReward(depositAmount);
    }

    private Double getReward(final Double depositAmount) {
        final double reward = depositAmount * PROMOTION_REWARD_PERCENTAGE;

        return reward > PROMOTION_REWARD_LIMIT
                ? PROMOTION_REWARD_LIMIT
                : reward;
    }
}
