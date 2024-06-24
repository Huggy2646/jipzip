USE `jipzip` ;

DROP TABLE IF EXISTS `jipzip`.`comments` ;
DROP TABLE IF EXISTS `jipzip`.`posts`;
DROP TABLE IF EXISTS `jipzip`.`qna_posts` ;
DROP TABLE IF EXISTS `jipzip`.`notice_posts` ;

CREATE TABLE qna_posts(
    postId INT AUTO_INCREMENT,
    userId varchar(45),
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lastModifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    hit INT DEFAULT 0,
    state ENUM('답변 대기', '답변 완료') DEFAULT '답변 대기',
    PRIMARY KEY (postId),
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE Set Null
);


CREATE TABLE comments (
    commentId INT AUTO_INCREMENT PRIMARY KEY,
    postId INT NOT NULL,
    commentContent TEXT NOT NULL,
    commenter varchar(45),
    heart INT DEFAULT 0,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lastModifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (postId) REFERENCES qna_posts(postId) ON DELETE CASCADE,
    FOREIGN KEY (commenter) REFERENCES Users(id) ON DELETE Set Null
);

CREATE TABLE notice_posts(
    postId INT AUTO_INCREMENT,
    userId varchar(45),
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    createdAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    lastModifiedAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    hit INT DEFAULT 0,
    PRIMARY KEY (postId),
    FOREIGN KEY (userId) REFERENCES Users(id) ON DELETE Set Null
);

DROP TRIGGER IF EXISTS `update_comments_lastModifiedAt`;
DROP TRIGGER IF EXISTS `update_qna_posts_lastModifiedAt`;
DROP TRIGGER IF EXISTS `update_notice_posts_lastModifiedAt`;

-- 댓글 내용이 바뀌면 lastModifiedAt을 자동으로 현재 시간으로 업데이트 해주는 트리거
DELIMITER //

CREATE TRIGGER `update_comments_lastModifiedAt`
BEFORE UPDATE ON `comments`
FOR EACH ROW
BEGIN
  IF NEW.commentContent!= OLD.commentContent THEN
    SET NEW.lastModifiedAt = NOW();
  END IF;
END //

DELIMITER ;

-- QnA 게시판의 게시글 제목 혹은 내용이 바뀌면 lastModifiedAt을 자동으로 현재 시간으로 업데이트 해주는 트리거
DELIMITER //

CREATE TRIGGER `update_qna_posts_lastModifiedAt`
BEFORE UPDATE ON `qna_posts`
FOR EACH ROW
BEGIN
  IF NEW.title != OLD.title OR NEW.content != OLD.content THEN
    SET NEW.lastModifiedAt = NOW();
  END IF;
END //

DELIMITER ;

-- 공지사항 게시판의 게시글 제목 혹은 내용이 바뀌면 lastModifiedAt을 자동으로 현재 시간으로 업데이트 해주는 트리거
DELIMITER //

CREATE TRIGGER `update_notice_posts_lastModifiedAt`
BEFORE UPDATE ON `notice_posts`
FOR EACH ROW
BEGIN
  IF NEW.title != OLD.title OR NEW.content != OLD.content THEN
    SET NEW.lastModifiedAt = NOW();
  END IF;
END //

DELIMITER ;
