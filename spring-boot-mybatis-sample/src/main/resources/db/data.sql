-- --------------------------------------------------------
-- 主机:                           centos-vm
-- 服务器版本:                        8.0.18 - MySQL Community Server - GPL
-- 服务器OS:                        Linux
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

INSERT INTO `author` (`id`, `username`, `password`, `email`, `bio`, `favourite_section`, `create_time`, `update_time`, `version`, `deleted`) VALUES
(1, 'admin', 'admin', 'mybatis@hao123.com', 'the java orm framwork', 'no one', '2020-01-14 06:12:18', '2020-01-14 06:13:11', 1, b'0');

INSERT INTO `blog` (`id`, `author_id`, `title`, `create_time`, `update_time`, `version`, `deleted`) VALUES
(1, 1, 'no title', '2020-01-14 14:13:26', '2020-01-14 14:13:27', 1, b'0');

INSERT INTO `comment` (`id`, `post_id`, `name`, `comment`, `create_time`, `update_time`, `version`, `deleted`) VALUES
(1, 1, 'funny', 'funny', '2020-01-14 14:15:52', '2020-01-14 14:15:53', 1, b'0'),
(2, 2, 'happy', 'happy', '2020-01-14 14:16:03', '2020-01-14 06:16:22', 1, b'0');

INSERT INTO `post` (`id`, `author_id`, `blog_id`, `subject`, `section`, `draft`, `body`, `create_time`, `update_time`, `version`, `deleted`) VALUES
(1, 1, 1, 'hello everyone', 'say hello to everyone', 'empty', 'just say hello', '2020-01-14 14:14:08', '2020-01-14 14:14:09', 1, b'0'),
(2, 1, 1, 'hi everyone', 'say hello to someone', 'just say hello', 'empty', '2020-01-14 14:14:56', '2020-01-14 14:14:56', 1, b'0');

INSERT INTO `tag` (`id`, `post_id`, `name`, `create_time`, `update_time`, `version`, `deleted`) VALUES
(1, 1, 'java', '2020-01-14 14:15:09', '2020-01-14 14:15:10', 1, b'0'),
(2, 2, 'go', '2020-01-14 14:15:19', '2020-01-14 14:15:19', 1, b'0');
