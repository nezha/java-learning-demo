package com.nezha.learn.demo.stream.group;

import io.vavr.Tuple2;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Copyright (C), 2018-2019, open source
 * FileName: GroupSample
 *
 * @author: chentong
 * Date:     2019/1/3 20:23
 */
public class GroupSample {
    public static void main(String[] args) throws Exception {
        // 待处理的数据源
        List<Article> articles = new ArrayList<>();

        // group by author
        Map<String, List<Article>> byAuthor = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor));
        System.out.println(byAuthor);

        // group by article type
        Map<ArticleType, List<Article>> byType = articles.stream()
                .collect(Collectors.groupingBy(article -> article.getType()));
        System.out.println(byType);

        // return complex key value of group
        Map<Tuple2<String, ArticleType>, List<Article>> complexKeMap = articles.stream()
                .collect(Collectors.groupingBy(article -> new Tuple2<>(article.getAuthor(), article.getType())));
        System.out.println(complexKeMap);

        // modify the return type Map<String,List> to Map<String,Set>
        Map<String, Set<Article>> authorSet = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.toSet()));
        System.out.println(authorSet.keySet());

        // providing a Secondary Group By Collector,group by author then group by type
        Map<String, Map<ArticleType, List<Article>>> chainGroup = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.groupingBy(Article::getType)));


        // group and reduce the result
        Map<String, Double> averageLike = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.averagingInt(Article::getLikes)));
        System.out.println(averageLike);

        Map<String, Integer> sumLike = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.summingInt(Article::getLikes)));
        System.out.println(sumLike);

        // 计算每位作者最受欢迎的文章
        Map<String, Optional<Article>> maxLikeByAuthor = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.maxBy(Comparator.comparingInt(Article::getLikes))));
        System.out.println(maxLikeByAuthor);

        // 计算每位作业最不受环境的文章
        Map<String, Optional<Article>> minLikeByAuthor = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.minBy(Comparator.comparingInt(Article::getLikes))));
        System.out.println(minLikeByAuthor);

        // 统计数据
        Map<String, IntSummaryStatistics> summaryStatistics = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.summarizingInt(Article::getLikes)));
        System.out.println(summaryStatistics);

        // Mapping Grouped Results to a Different Type
        Map<String, String> modifyGroupType = articles.stream()
                .collect(Collectors.groupingBy(Article::getAuthor, Collectors.mapping(Article::getTitle, joining("||"))));
        System.out.println(modifyGroupType);

        // Concurrent Grouping By Collector
        ConcurrentMap<String,List<Article>> concurrentGroup = articles.parallelStream()
                .collect(Collectors.groupingByConcurrent(Article::getAuthor));
    }
}
