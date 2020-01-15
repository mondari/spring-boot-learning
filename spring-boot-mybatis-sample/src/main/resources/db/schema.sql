-- 一篇博客，它由某位作者所写，有很多的博文，每篇博文有零或多条的评论和标签

CREATE TABLE `author`
(
    `id`                INT(11)      NOT NULL,
    `username`          VARCHAR(255) NOT NULL DEFAULT 'admin' COMMENT '用户名',
    `password`          VARCHAR(255) NOT NULL DEFAULT 'admin' COMMENT '密码',
    `email`             VARCHAR(255) NULL     DEFAULT NULL COMMENT '邮箱',
    `bio`               VARCHAR(255) NULL     DEFAULT NULL COMMENT '简介',
    `favourite_section` VARCHAR(255) NULL     DEFAULT NULL COMMENT '收藏夹',
    `create_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version`           INT(11)      NOT NULL DEFAULT '1' COMMENT '版本',
    `deleted`           BIT(1)       NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0不删 1删除',
    PRIMARY KEY (`id`)
)
    COMMENT ='作者表'
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
;


CREATE TABLE `blog`
(
    `id`          INT(11)      NOT NULL,
    `author_id`   INT(11)      NOT NULL COMMENT '作者ID',
    `title`       VARCHAR(255) NOT NULL COMMENT '博客名',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version`     INT(11)      NOT NULL DEFAULT '1' COMMENT '版本',
    `deleted`     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0不删 1删除',
    PRIMARY KEY (`id`)
)
    COMMENT ='博客表'
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
;


CREATE TABLE `post`
(
    `id`          INT(11)      NOT NULL,
    `author_id`   INT(11)      NOT NULL COMMENT '作者ID',
    `blog_id`     INT(11)      NOT NULL COMMENT '博客ID',
    `subject`     VARCHAR(255) NOT NULL COMMENT '主题',
    `section`     VARCHAR(255) NULL     DEFAULT NULL COMMENT '章节',
    `draft`       VARCHAR(255) NULL     DEFAULT NULL COMMENT '草稿',
    `body`        VARCHAR(255) NULL     DEFAULT NULL COMMENT '内容',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version`     INT(11)      NOT NULL DEFAULT '1' COMMENT '版本',
    `deleted`     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0不删 1删除',
    PRIMARY KEY (`id`)
)
    COMMENT ='博客文章表'
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
;


CREATE TABLE `comment`
(
    `id`          INT(11)      NOT NULL,
    `post_id`     INT(11)      NOT NULL COMMENT '博文ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '评论名称',
    `comment`     VARCHAR(255) NOT NULL COMMENT '评论内容',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version`     INT(11)      NOT NULL DEFAULT '1' COMMENT '版本',
    `deleted`     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0不删 1删除',
    PRIMARY KEY (`id`)
)
    COMMENT ='评论表'
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
;


CREATE TABLE `tag`
(
    `id`          INT(11)      NOT NULL,
    `post_id`     INT(11)      NOT NULL COMMENT '文章ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '标签名',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `version`     INT(11)      NOT NULL DEFAULT '1' COMMENT '版本',
    `deleted`     BIT(1)       NOT NULL DEFAULT b'0' COMMENT '逻辑删除 0不删 1删除',
    PRIMARY KEY (`id`)
)
    COMMENT ='标签表'
    COLLATE = 'utf8mb4_0900_ai_ci'
    ENGINE = InnoDB
;

