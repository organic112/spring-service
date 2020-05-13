-- creates table common for jdbc and jpa locks

CREATE TABLE `doc_locks` (
  `doc_locks_id` varchar(50) NOT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_date` datetime(6) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  `doc_code` varchar(50) NOT NULL,
  `doc_id` varchar(30) NOT NULL,
  `doc_type` varchar(30) NOT NULL,
  `login` varchar(255) NOT NULL,
  PRIMARY KEY (`doc_locks_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

