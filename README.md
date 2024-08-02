readme


CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `phone` int DEFAULT NULL,
  PRIMARY KEY (`id`)
)


CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `discount` int NOT NULL,
  `discount_price` double DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `is_active` int NOT NULL,
  `price` double DEFAULT NULL,
  `stock` int NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)

SELECT * FROM bandingkan_db.product_table;
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (1,'Skincare','60ml x 1 botol',5,114,'1.png',1,120,100,'Skinzen Face Oil');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (2,'Skincare','Zinnia Magenta Facial Cleanser (100ml)',10,31.5,'2.png',1,35,100,'Zinnia Magenta');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (3,'Skincare','Bio Miracle Action Serum (20ml)',9,35.49,'3.png',1,39,100,'Bio Miracle Action Serum');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (4,'Supplemen Dalaman','Mushtanir (400ml)',10,108,'5.png',1,120,120,'Mushtanir');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (5,'Supplemen Dalaman','(30ml x 16 botol)',5,142.5,'19.png',1,150,10,'Nutri Hi-b');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (6,'Supplemen Dalaman','(30ml x 16 botol)',5,123.5,'20.png',1,130,10,'Flexy Drink');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (7,'Minuman Berkhasiat','Jus minuman yang kaya dengan sumber antioksidan',9,26.39,'21.png',1,29,10,'Ice Tea Peach Mengkudu');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (8,'Minuman Berkhasiat','(15 sachet)',10,40.5,'26.png',1,45,10,'Romagella Coffee');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (9,'Minuman Berkhasiat','Revolusi keenakan kopi dengan campuran herba Ginkgo biloba, Ginseng Goryeo, Bacopa Monnieri, dan Stevia',8,44.16,'27.png',1,48,10,'Kopi Ratu');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (10,'Bodycare','Sabun herba yang mengandungi Susu Kambing, Tea Tree, dan Hyaluronate',15,15.3,'13.png',1,18,10,'Super Moist Soap');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (11,'Bodycare','Sabun anti-fungal untuk kesihatan kulit badan',10,20.7,'14.png',1,23,10,'Eukalypto Soap');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (12,'Bodycare','Ekstrak herba Tea Tree, Daun Sireh, Kacip Fatimah dan Gamat',10,19.8,'17.png',1,22,10,'Putik Telaga Dara');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (13,'Aromaterapi','Kombinasi ekstrak herba yang unik seperti susu kambing, buah pala, daun melong, lengkuas, serai, rumpai laut, oren pahit, dan rosemary',5,46.55,'18.png',1,49,10,'Oil of Javanica');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (14,'Aromaterapi','Merupakan rawatan terapeutik yang menggunakan terapi sejuk (cryotherapy) melalui semburan aromaterapi yang diformulasikan untuk melegakan kesakitan',5,37.05,'30.png',1,39,10,'Herbal Cryo Spray');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (15,'Aromaterapi','Minyak angin aromaterapi eksklusif ini diasaskan daripada herba Mentha Piperita yang merupakan sebatian organik minyak pudina Jepun yang mengandungi kadungan menthol yang tinggi khasiatnya',10,15.3,'32.png',1,17,10,'Cenanga Oil');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (16,'Rumah Hutan','Minyak aromaterapi yang bersifat teraputik ekstrak daripada herba dan bunga-bungaan yang memberikan manfaat perubatan secara holistik terhadap kesihatan dengan meningkatkan sistem imun dan penyembuhan kendiri',0,70,'6.png',1,70,10,'Minyak Floral');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (17,'Rumah Hutan','Herba-herba yang terkandung di dalam minyak ini mempunyai manfaat perubatan terbaik terhadap kesihatan saraf, sendi,, kulit dan banyak lagi',0,110,'8.png',1,110,10,'Khelin Myrha');
INSERT INTO `product_table` (`id`,`category`,`description`,`discount`,`discount_price`,`image`,`is_active`,`price`,`stock`,`title`) VALUES (18,'Rumah Hutan','Minyak pati herba asia dan campuran minyak-minyak dari Tanah Arab. Ia juga digunakan sebagai rawatan luaran untuk membantu mengecutkan ketumbuhan, masalah kulit dan luka',0,110,'9.png',1,110,10,'Jatro Phora');


