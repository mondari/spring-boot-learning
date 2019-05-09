DROP TABLE IF EXISTS `point`;
CREATE TABLE `point`
(
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
  `x`           DOUBLE      NOT NULL,
  `y`           DOUBLE      NOT NULL,
  `z`           DOUBLE      NOT NULL,
  `create_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
  `update_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name` (`name`)
)
  COLLATE ='utf8mb4_general_ci'
  ENGINE=InnoDB
;
