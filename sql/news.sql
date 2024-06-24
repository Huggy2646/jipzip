use jipzip;

drop table IF EXISTS `jipzip`.`news`; 


create table news(
	newsId int AUTO_INCREMENT, 
    newsTitle TEXT,
    newsUrl TEXT,
    PRIMARY KEY (newsId)
);


insert into news values
(0,'"절대 이사 못 들어온다" 부글부글…대구 아파트서 무슨 일이','https://n.news.naver.com/article/015/0004986369'),
(0,'[단독] “당첨되면 차익 20억원”…반포 원펜타스, 후분양에도 싸게 나오는 이유', 'https://n.news.naver.com/article/009/0005305452'),
(0,'심각한 전세시장, 앞으로가 더 문제입니다 [더 머니이스트-심형석의 부동산정석]','https://n.news.naver.com/article/015/0004986372'),
(0,'전세 대신 월세살이 택하는 신혼부부…“목돈 아껴 향후 투자”','https://n.news.naver.com/article/020/0003565434'),
(0,'재출발하는 상계주공5 재건축…`분담금 5억`도 버거운데','https://n.news.naver.com/article/029/0002874452');

select * from news;



