/*
 Navicat Premium Data Transfer

 Source Server         : 354Project
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : cmpt354project

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 18/04/2021 17:57:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for collectdoc
-- ----------------------------
DROP TABLE IF EXISTS `collectdoc`;
CREATE TABLE `collectdoc`  (
  `docID` int NOT NULL,
  `userID` int NOT NULL,
  `libraryID` int NOT NULL,
  PRIMARY KEY (`userID`, `docID`, `libraryID`) USING BTREE,
  INDEX `docID`(`docID`) USING BTREE,
  INDEX `libraryID`(`libraryID`) USING BTREE,
  CONSTRAINT `collectdoc_ibfk_1` FOREIGN KEY (`docID`) REFERENCES `document` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collectdoc_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collectdoc_ibfk_3` FOREIGN KEY (`libraryID`) REFERENCES `library` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collectdoc
-- ----------------------------
INSERT INTO `collectdoc` VALUES (100002, 1004173716, 320156);
INSERT INTO `collectdoc` VALUES (100003, 1004183965, 320137);
INSERT INTO `collectdoc` VALUES (100005, 1004175218, 320155);
INSERT INTO `collectdoc` VALUES (100005, 1004184753, 320274);
INSERT INTO `collectdoc` VALUES (100009, 1004175218, 320155);

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `course` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fileName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'New File',
  `type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `URL` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ID`, `userID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID`) USING BTREE,
  INDEX `userID`(`userID`) USING BTREE,
  CONSTRAINT `document_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 100010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES (100001, 1004175218, 'CMPT 354', 'cmpt 354 ass1', 'pdf', 'src\\main\\resources\\file\\cmpt 354 ass1100012.pdf');
INSERT INTO `document` VALUES (100002, 1004173716, 'CMPT 354', 'cmpt 354 ass1', 'pdf', 'src\\main\\resources\\file\\cmpt 354 ass11.pdf');
INSERT INTO `document` VALUES (100003, 1004171432, 'CMPT 363', 'cmpt 363 ass1', 'pdf', 'src\\main\\resources\\file\\cmpt 363 ass1100011.pdf');
INSERT INTO `document` VALUES (100005, 1004183965, 'CMPT 225', 'BinaryTree-1', 'pdf', 'src\\main\\resources\\file\\BinaryTree-1100003.pdf');
INSERT INTO `document` VALUES (100009, 1004184753, 'MATH 251', '1-Fall2019-HW7', 'pdf', 'src\\main\\resources\\file\\1-Fall2019-HW7100004.pdf');

-- ----------------------------
-- Table structure for library
-- ----------------------------
DROP TABLE IF EXISTS `library`;
CREATE TABLE `library`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'new library',
  PRIMARY KEY (`ID`, `userID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID`) USING BTREE,
  INDEX `userID`(`userID`) USING BTREE,
  CONSTRAINT `library_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 320277 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of library
-- ----------------------------
INSERT INTO `library` VALUES (320129, 1004184752, 'Exam');
INSERT INTO `library` VALUES (320137, 1004183965, 'Assignment');
INSERT INTO `library` VALUES (320155, 1004175218, 'new library');
INSERT INTO `library` VALUES (320156, 1004173716, 'Lecture');
INSERT INTO `library` VALUES (320187, 1004171432, 'Note');
INSERT INTO `library` VALUES (320274, 1004184753, 'Quiz');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `pName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `postContent` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`ID`, `userID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID`) USING BTREE,
  INDEX `userID`(`userID`) USING BTREE,
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 400278562 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (400224786, 1004173716, 'wrong in lecture', 'the value in the slide 6 is incorrect!');
INSERT INTO `post` VALUES (400232478, 1004171432, 'Assignment typo', 'this pdf does not include an ER diagram!');
INSERT INTO `post` VALUES (400236547, 1004183965, 'Newton first low', 'I do not know what that is, please change your question!');
INSERT INTO `post` VALUES (400245893, 1004171432, 'question1 assignment2', 'how to solve the first task?');
INSERT INTO `post` VALUES (400252143, 1004184752, 'Lab time', 'out next lab time will at 6am!');
INSERT INTO `post` VALUES (400278561, 1004183965, 'Lecture video', 'please upload the latest lecture video!');

-- ----------------------------
-- Table structure for postmessage
-- ----------------------------
DROP TABLE IF EXISTS `postmessage`;
CREATE TABLE `postmessage`  (
  `postID` int NOT NULL,
  `userID` int NOT NULL,
  `message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `time_stamp` timestamp NOT NULL,
  `author` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`userID`, `postID`, `message`) USING BTREE,
  INDEX `postID`(`postID`) USING BTREE,
  CONSTRAINT `postmessage_ibfk_1` FOREIGN KEY (`postID`) REFERENCES `post` (`ID`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `postmessage_ibfk_2` FOREIGN KEY (`userID`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of postmessage
-- ----------------------------
INSERT INTO `postmessage` VALUES (400236547, 1004171432, 'I do not know how to understand it', '2021-04-18 10:10:34', 'Zhong Li');
INSERT INTO `postmessage` VALUES (400224786, 1004173716, 'There should no underline', '2021-04-18 10:10:34', 'Wang Wu');
INSERT INTO `postmessage` VALUES (400236547, 1004173716, 'Is it possible to realize it', '2021-04-18 10:10:34', 'Wang Wu');
INSERT INTO `postmessage` VALUES (400245893, 1004173716, 'I give up it', '2021-04-18 10:10:34', 'Wang Wu');
INSERT INTO `postmessage` VALUES (400252143, 1004183965, 'The time is unsuitable', '2021-04-18 10:10:34', 'Tim');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `ID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `level` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `ID`(`ID`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004184755 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1004171432, 'Li Zhong', 'yuanshen@gmail.com', 'Juj511956', 1);
INSERT INTO `user` VALUES (1004173716, 'Wu Wang', 'fangsi@163.com', '$ww488156', 0);
INSERT INTO `user` VALUES (1004175218, 'San Zhang', 'afjafj@gmail.com', 'Juj511956', 0);
INSERT INTO `user` VALUES (1004183965, 'Tim Docan', 'Tim347@gmail.com', 'Tdo478956', 0);
INSERT INTO `user` VALUES (1004184752, 'James May', 'James333@gmail.com', 'May5587356', 0);

-- ----------------------------
-- Triggers structure for table post
-- ----------------------------
DROP TRIGGER IF EXISTS `Post_delete`;
delimiter ;;
CREATE TRIGGER `Post_delete` AFTER DELETE ON `post` FOR EACH ROW BEGIN
DELETE FROM postmessage WHERE postID = OLD.ID;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
