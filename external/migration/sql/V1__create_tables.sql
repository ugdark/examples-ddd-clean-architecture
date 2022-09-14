CREATE TABLE `user`
(
  `id`         bigint unsigned NOT NULL COMMENT '識別子',
  `name`       varchar(255) NOT NULL COMMENT '名前',
  `password`   varchar(255) NOT NULL COMMENT '暗号化されたパスワード',
  `created_at` datetime     NOT NULL COMMENT '作成日時',
  `updated_at` datetime     NOT NULL COMMENT '更新日時',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザー';

CREATE TABLE `user_id_numbering`
(
  `id` bigint unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='ユーザーid採番';

INSERT INTO `user_id_numbering` (`id`)
VALUES ('0');
