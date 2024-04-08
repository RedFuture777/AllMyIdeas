package org.example.controller;

import org.example.util.RedisUtils;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/redis")
public class RedisController {


    private static final String SCORE_RANK = "score_rank";

    @Resource
    private RedisUtils redisUtils;

    @GetMapping("/getRankListOfTop10")
    public Set<ZSetOperations.TypedTuple<String>> getRankListOfTop10() {
        Set<ZSetOperations.TypedTuple<String>> strings = redisUtils.zReverseRangeWithScores(SCORE_RANK, 0, 9);
        System.out.println(strings);
        return strings;
    }

}
