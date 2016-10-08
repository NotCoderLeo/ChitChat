CREATE TABLE IF NOT EXISTS users (
  id           INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  username     VARCHAR(125) NOT NULL,
  password     TEXT         NOT NULL,
  displayName  VARCHAR(350) NULL,
  created_at   TIMESTAMP    NULL                 DEFAULT CURRENT_TIMESTAMP,
  mood         VARCHAR(450) NULL,
  cachedStatus INT          NULL                 DEFAULT 0,
  avatarUrl    TEXT         NULL,
  INDEX un_id_cmpd_idx (username, id),
  UNIQUE INDEX (username)
)
  CHARSET = UTF8
  ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS conversations (
  id         INT PRIMARY KEY       AUTO_INCREMENT,
  name       VARCHAR(750) NULL,
  avatarUrl  TEXT         NULL,
  leaderId   INT          NULL,
  isGroup    BOOLEAN      NOT NULL DEFAULT FALSE,
  created_at TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP    NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  INDEX nm_id_cmpd_idx (name, id)
)
  CHARSET = UTF8
  ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS conversation_members (
  user_id         INT       NOT NULL,
  conversation_id INT       NOT NULL,
  added           TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
)
  CHARSET = UTF8
  ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS messages (
  id              INT PRIMARY KEY       AUTO_INCREMENT,
  user_id         INT       NOT NULL,
  conversation_id INT       NOT NULL,
  created_at      TIMESTAMP NULL        DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP NULL        DEFAULT CURRENT_TIMESTAMP,
  body            LONGTEXT  NOT NULL,
  INDEX (user_id, conversation_id)
)
  CHARSET = UTF8
  ENGINE = INNODB;