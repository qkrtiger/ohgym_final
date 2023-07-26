-- drop table board;
-- drop table gym;
-- drop table gymconvenient;
-- drop table gymgoods;
-- drop table gymmachine;
-- drop table gymmark;
-- drop table member;
-- drop table mpay;
-- drop table reply;
-- drop table review;
-- drop table traingoods;

-- drop view if exists blist;
-- drop view if exists fillter;
-- drop view if exists fillter2;
-- drop view if exists fillter_fin;
-- drop view if exists gmlist;
-- drop view if exists gymcm;
-- drop view if exists maptrain;
-- drop view if exists re;
-- drop view if exists ready2;
-- drop view if exists relist;
-- drop view if exists rlist;
-- drop view if exists tmember;
-- drop view if exists train;
-- drop view if exists mainboard;
-- drop view if exists gymmember;


 commit;

-- 삭제
-- DROP TABLE IF EXISTS `GymConvenient`;
-- DROP TABLE IF EXISTS `GymMark`;
-- DROP TABLE IF EXISTS `Reply`;
-- DROP TABLE IF EXISTS `Board`;
-- DROP TABLE IF EXISTS `Review`;
-- DROP TABLE IF EXISTS `mpay`;
-- DROP TABLE IF EXISTS `traingoods`;
-- DROP TABLE IF EXISTS `GymGoods`;
-- DROP TABLE IF EXISTS `Member`;
﻿-- DROP TABLE IF EXISTS `GymMachine`;
-- DROP TABLE IF EXISTS `Gym`;

-- 전체 테이블
-- 회원
CREATE TABLE `member` (
  `membernum` int NOT NULL AUTO_INCREMENT,
  `mid` varchar(20) NOT NULL,
  `mpass` varchar(100) NOT NULL,
  `mmail` varchar(40) NOT NULL,
  `mname` varchar(10) NOT NULL,
  `mbirth` date NOT NULL,
  `mgender` varchar(5) NOT NULL,
  `mphone` varchar(13) NOT NULL,
  `mnickname` varchar(20) NOT NULL,
  `msysname` varchar(50) DEFAULT NULL,
  `mresolution` varchar(100) DEFAULT NULL,
  `mregion` varchar(20) DEFAULT NULL,
  `gymnum` int DEFAULT NULL,
  `tcareer` varchar(200) DEFAULT NULL,
  `tlicence` varchar(100) DEFAULT NULL,
  `tpr` varchar(200) DEFAULT NULL,
  `tsysname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`membernum`)
) ;

-- 헬스장
CREATE TABLE `gym` (
  `gymnum` int NOT NULL AUTO_INCREMENT,
  `gname` varchar(20) NOT NULL,
  `gregistnum` int NOT NULL,
  `gid` varchar(20) NOT NULL,
  `gpass` varchar(100) NOT NULL,
  `gphone` varchar(13) NOT NULL,
  `glocation` varchar(50),
  `gdescription` text,
  `gnotice` varchar(300) DEFAULT NULL,
  `gaddservfree` varchar(100) DEFAULT NULL,
  `gaddservpay` varchar(100) DEFAULT NULL,
  `goperation` varchar(100) DEFAULT NULL,
  `gsysname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`gymnum`)
);

-- 헬스장 상품
CREATE TABLE `gymgoods` (
  `ggoodsnum` int NOT NULL AUTO_INCREMENT,
  `ggoodsname` varchar(45) NOT NULL,
  `ggoodsprice` int DEFAULT '0',
  `ggoodsaction` tinyint(1) NOT NULL,
  `ggoodsadd` varchar(100) DEFAULT NULL,
  `ggoodsperiod` varchar(30) NOT NULL,
  `gymnum` int NOT NULL,
  PRIMARY KEY (`ggoodsnum`),
  KEY `FK_Gym_TO_GymGoods_1` (`gymnum`),
  CONSTRAINT `FK_Gym_TO_GymGoods_1` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
);

-- insert into gymgoods values(null, '회원권', 1, 1, '추가사항 따윈 없다', '3개월', 1); 
-- insert into gymgoods values(null, '회원권', 1, 0, '추가사항 따윈 없다2', '6개월', 1); 
-- insert into gymgoods values(null, '일일권', 1, 1, '추가사항 따윈 없다3', '1일', 1); 

-- 트레이너 상품
CREATE TABLE `traingoods` (
  `tgoodsint` int NOT NULL AUTO_INCREMENT,
  `tgoodsname` varchar(45) NOT NULL,
  `tgoodsturn` tinyint(1) NOT NULL,
  `tgoodscontents` varchar(45) DEFAULT NULL,
  `tgoodsprice` int DEFAULT '0',
  `tgoodsnum` int DEFAULT '0',
  `membernum` int NOT NULL,
  `gymnum` int NOT NULL,
  PRIMARY KEY (`tgoodsint`),
  KEY `membernum` (`membernum`),
  KEY `gymnum` (`gymnum`),
  CONSTRAINT `traingoods_ibfk_1` FOREIGN KEY (`membernum`) REFERENCES `member` (`membernum`),
  CONSTRAINT `traingoods_ibfk_2` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
);

-- insert into traingoods values(null, '10회', 1, '추가사항 따윈 없다', 1, 10, 2, 1); 

commit;

-- 회원결제
CREATE TABLE `mpay` (
  `mpaynum` varchar(100) NOT NULL,
  `membernum` int NOT NULL,
  `mpaymethod` varchar(20) NOT NULL,
  `mpayproduct` varchar(45) NOT NULL,
  `mpayprice` int DEFAULT '0',
  `mpaydate` varchar(45) NOT NULL,
  `mpaygym` varchar(20) NOT NULL,
  `mpayperiod` date NOT NULL,
  `mpaytime` varchar(45) NOT NULL,
  `trainername` varchar(45) null,
  `ggoodsnum` int,
  `tgoodsint` int,
  `gymnum` int NOT NULL,
   CONSTRAINT `PK_MPAY` PRIMARY KEY (`mpaynum`,`membernum`),
   CONSTRAINT `FK_Member_TO_MPay_1` FOREIGN KEY (`membernum`) REFERENCES `Member` (`membernum`),
   CONSTRAINT `FK_GymGoods_TO_MPay_1` FOREIGN KEY (`ggoodsnum`) REFERENCES `GymGoods` (`ggoodsnum`),
   CONSTRAINT `FK_TrainGoods_TO_MPay_1` FOREIGN KEY (`tgoodsint`) REFERENCES `TrainGoods` (`tgoodsint`),
   CONSTRAINT `FK_Gym_TO_MPay_1` FOREIGN KEY (`gymnum`) REFERENCES `Gym` (`gymnum`)
);

-- 구매 후기
CREATE TABLE `review` (
  `reviewnum` int NOT NULL AUTO_INCREMENT,
  `membernum` int NOT NULL,
  `gymnum` int NOT NULL,
  `mnickname` varchar(30) NOT NULL,
  `reviewcontents` varchar(200) NOT NULL,
  `reviewstar` float DEFAULT '0',
  `reviewdate` datetime DEFAULT CURRENT_TIMESTAMP,
  `mpaynum` varchar(100) NOT NULL,
  PRIMARY KEY (`reviewnum`),
  CONSTRAINT `FK_MPay_TO_Review_1` FOREIGN KEY (`membernum`) REFERENCES `Mpay` (`membernum`),
  CONSTRAINT `FK_MPay_TO_Review_2` FOREIGN KEY (`mpaynum`) REFERENCES `MPay` (`mpaynum`),
  CONSTRAINT `FK_Gym_TO_Review_1` FOREIGN KEY (`gymnum`) REFERENCES `Gym` (`gymnum`)
);

-- 게시판
CREATE TABLE `board` (
  `boardnum` int NOT NULL AUTO_INCREMENT,
  `membernum` int,
  `boardtitle` varchar(50) NOT NULL,
  `boardcontents` text NOT NULL,
  `boarddate` datetime DEFAULT CURRENT_TIMESTAMP,
  `boardcount` int NOT NULL DEFAULT '0',
  `mnickname` varchar(30),
  `gymnum` int,
  `gname` varchar(30),
  PRIMARY KEY (`boardnum`),
  KEY `FK_Member_TO_Borad_1` (`membernum`),
  CONSTRAINT `FK_Member_TO_Borad_1` FOREIGN KEY (`membernum`) REFERENCES `member` (`membernum`),
  CONSTRAINT `FK_Gym_TO_Borad_1` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
);

-- 게시판 댓글
CREATE TABLE `reply` (
  `rnum` int NOT NULL AUTO_INCREMENT,
  `boardnum` int NOT NULL,
  `mnickname` varchar(30),
  `gname` varchar(30),
  `rcontent` varchar(200) NOT NULL,
  `rdate` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`rnum`),
  KEY `FK_Board_TO_Reply_1` (`boardnum`),
  CONSTRAINT `FK_Board_TO_Reply_1` FOREIGN KEY (`boardnum`) REFERENCES `board` (`boardnum`)
);

-- 찜
CREATE TABLE `gymmark` (
  `membernum` int NOT NULL,
  `gymnum` int NOT NULL,
  KEY `FK_gym_TO_GymMark_1` (`gymnum`),
  CONSTRAINT `FK_gym_TO_GymMark_1` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
);

-- 헬스장 기구
CREATE TABLE `gymmachine` (
  `gymnum` int NOT NULL,
  `gm_latpull` tinyint(1) DEFAULT NULL,
  `gm_shoulderpress` tinyint(1) DEFAULT NULL,
  `gm_citydraw` tinyint(1) DEFAULT NULL,
  `gm_legpress` tinyint(1) DEFAULT NULL,
  `gm_smithmachine` tinyint(1) DEFAULT NULL,
  `gm_lyinglegcurl` tinyint(1) DEFAULT NULL,
  `gm_chestfly` tinyint(1) DEFAULT NULL,
  `gm_hyperextension` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`gymnum`),
  CONSTRAINT `FK_Gym_TO_GymMachine_1` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
); 

-- 헬스장 편의시설
CREATE TABLE `gymconvenient` (
  `gymnum` int NOT NULL,
  `gc_cloths` tinyint(1) DEFAULT NULL,
  `gc_towel` tinyint(1) DEFAULT NULL,
  `gc_wifi` tinyint(1) DEFAULT NULL,
  `gc_parking` tinyint(1) DEFAULT NULL,
  `gc_inbody` tinyint(1) DEFAULT NULL,
  `gc_sauna` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`gymnum`),
  CONSTRAINT `FK_Gym_TO_GymConvenient_1` FOREIGN KEY (`gymnum`) REFERENCES `gym` (`gymnum`)
);


-- 뷰
-- 트레이너 
CREATE VIEW TMember AS
    SELECT m.membernum, m.mid, m.mname, m.mbirth, m.mgender, 
           m.mphone, m.mnickname, m.msysname, 
           m.tcareer, m.tlicence, m.tpr,
           m.tsysname, g.gname, m.gymnum
    FROM Member m LEFT JOIN Gym g 
    ON m.gymnum = g.gymnum
    WHERE m.gymnum IS NOT NULL;

-- 찜 출력
create or replace view gmlist as
select gm.membernum, g.gymnum, g.gname, g.glocation, g.gsysname
from gymmark gm join gym g
on gm.gymnum = g.gymnum;

-- 헬스장 마이페이지 수정용
create view GymCM as
	select m.gymnum, m.gm_latpull, m.gm_shoulderpress, m.gm_citydraw, m.gm_legpress, 
        m.gm_smithmachine, m.gm_lyinglegcurl, m.gm_chestfly, m.gm_hyperextension, 
        c.gc_cloths, c.gc_towel, c.gc_wifi, c.gc_parking, c.gc_inbody, c.gc_sauna
from gymmachine m left join gymconvenient c
on m.gymnum = c.gymnum;

CREATE VIEW GymMember AS
    SELECT m.membernum, m.mid, m.mname, m.mbirth, m.mgender, 
           m.mphone, m.mnickname, m.msysname, 
           m.tcareer, m.tlicence, m.tpr,
           m.tsysname, g.gymnum, g.gname, g.gregistnum, 
           g.gid, g.gpass, g.gphone, g.glocation,
           g.gdescription, g.gnotice, g.gaddservfree,
           g.gaddservpay, g.goperation, g.gsysname 
    FROM TMember m LEFT JOIN Gym g 
    ON m.gymnum = g.gymnum
    WHERE m.gymnum IS NOT NULL;

-- 트레이너
CREATE OR REPLACE VIEW `train` AS
    SELECT 
        `member`.`membernum` AS `membernum`,
        `member`.`gymnum` AS `gymnum`,
        `member`.`tcareer` AS `tcareer`,
        `member`.`tlicence` AS `tlicence`,
        `member`.`tpr` AS `tpr`,
        `member`.`tsysname` AS `tsysname`,
        `member`.`mname` AS `mname`
    FROM
        `member`
    WHERE
        (`member`.`gymnum` IS NOT NULL);

-- 지도 트레이너검색
CREATE OR REPLACE VIEW `maptrain` AS
    SELECT 
        `m`.`mname` AS `mname`,
        `m`.`gymnum` AS `gymnum`,
        `g`.`gname` AS `gname`,
        `g`.`glocation` AS `glocation`
    FROM
        (`member` `m`
        JOIN `gym` `g`)
    WHERE
        (`m`.`gymnum` = `g`.`gymnum`);

-- 최신 순으로 리뷰 출력 view
CREATE OR REPLACE VIEW `re` AS
    SELECT 
        `r`.`gymnum` AS `gymnum`,
        `r`.`mnickname` AS `mnickname`,
        `r`.`reviewcontents` AS `reviewcontents`,
        `r`.`reviewstar` AS `reviewstar`,
        `r`.`reviewdate` AS `reviewdate`,
        `m`.`msysname` AS `msysname`,
        `r`.`reviewnum` AS `reviewnum`
    FROM
        (`ohgym_db`.`member` `m`
        JOIN `ohgym_db`.`review` `r`)
    WHERE
        (`m`.`membernum` = `r`.`membernum`);

CREATE OR REPLACE VIEW `relist` AS
    SELECT 
        `r`.`gymnum` AS `gymnum`,
        `r`.`mnickname` AS `mnickname`,
        `r`.`reviewcontents` AS `reviewcontents`,
        `r`.`reviewstar` AS `reviewstar`,
        `r`.`reviewdate` AS `reviewdate`,
        `r`.`msysname` AS `msysname`,
        `r`.`reviewnum` AS `reviewnum`
    FROM
        `re` `r`
    WHERE
        (0 <> `r`.`gymnum`)
    ORDER BY `r`.`reviewdate` DESC;

-- 최신글 순으로 댓글 출력 VIEW
CREATE OR REPLACE VIEW RLIST AS
SELECT rnum, boardnum, mnickname, gname, rcontent, rdate 
FROM Reply 
ORDER BY rdate DESC;

-- 최신글순으로 원글 출력 VIEW
CREATE OR REPLACE VIEW BLIST AS
SELECT B.boardnum,    -- BNUM:빈 필드명과 컬럼명이 일치하면 편하다. 
       B.boardtitle,
       B.boardcontents,
       B.mnickname,
       B.boarddate,
       B.boardcount 
FROM board B INNER JOIN member M
ON B.mnickname = M.mnickname
ORDER BY B.boarddate DESC;


-- 메인 게시판 출력용
CREATE OR REPLACE VIEW mainboard AS
SELECT B.boardnum,    -- BNUM:빈 필드명과 컬럼명이 일치하면 편하다. 
       B.boardtitle,
       B.boardcontents,
       B.mnickname,
       B.boarddate,
       B.boardcount, 
       m.msysname
FROM BLIST B INNER JOIN member M
ON B.mnickname = M.mnickname
ORDER BY B.boardcount DESC
limit 3;


-- 필터 관련 뷰 모음
create or replace view  ready2 as
SELECT D.ggoodsname, G.gymnum, G.gname, G.glocation, G.gsysname 
FROM gymgoods D, gym G
where D.gymnum = G.gymnum
;

create or replace view fillter as
SELECT  M.mgender, G.glocation, G.gsysname, G.ggoodsname, G.gymnum, G.gname 
FROM member M, ready2 G
where M.gymnum = G.gymnum;
;

CREATE OR REPLACE VIEW fillter2 AS
SELECT mgender, glocation, gsysname, ggoodsname, gname, gc.*
FROM fillter f 
JOIN gymconvenient gc
ON f.gymnum = gc.gymnum
;

CREATE OR REPLACE VIEW fillter_fin AS
SELECT mgender, glocation, gsysname, ggoodsname, gname, 
	gc_cloths, gc_towel, gc_wifi, gc_parking, gc_inbody, gc_sauna, gm.*
FROM fillter2 f
JOIN gymmachine gm
ON f.gymnum = gm.gymnum
;

commit;