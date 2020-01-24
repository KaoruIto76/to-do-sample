/**
 *
 * init sql
 *
 */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `slug` VARCHAR(255) NOT NULL,
  `category_color` TINYINT NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO category(name,slug,1) values('フロントエンド','front');
INSERT INTO category(name,slug,2) values('バックエンド','back');
INSERT INTO category(name,slug,3) values('インフラ','infra');


CREATE TABLE `to-do` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` bigint(20) unsigned NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `body` VARCHAR(255) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO to-do(category_id,title,body) values(1, 'デザインをいい感じにする','ヘッダーのデザインをもっといい感じに');
INSERT INTO to-do(category_id,title,body) values(2, 'Controllerの修正','Controller名をもっといい感じに');
INSERT INTO to-do(category_id,title,body) values(3, '新しいDB環境の作成','タイトル通り');


