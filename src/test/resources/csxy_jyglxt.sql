/*
 Navicat Premium Data Transfer

 Source Server         : MySQL5.7
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3307
 Source Schema         : csxy_jyglxt

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 08/10/2020 19:33:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_paramter
-- ----------------------------
DROP TABLE IF EXISTS `sys_paramter`;
CREATE TABLE `sys_paramter`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `NAME` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `TYPECODE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数类型编码',
  `VALUE` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `SEQ` int(255) NULL DEFAULT NULL COMMENT '排序',
  `CREATEDATE` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `YXZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态（1表示有效、0表示无效）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_paramter
-- ----------------------------
INSERT INTO `sys_paramter` VALUES ('0eec1c3a1aee11ea9e8968f728cf81d0', '男性', 'XB', '1', 1, '2019-12-10 09:40:34', '1');
INSERT INTO `sys_paramter` VALUES ('2a89a48b1aee11ea9e8968f728cf81d0', '女性', 'XB', '2', 2, '2019-12-10 09:41:20', '1');
INSERT INTO `sys_paramter` VALUES ('387651921aee11ea9e8968f728cf81d0', '未知', 'XB', '9', 3, '2019-12-10 09:41:44', '1');
INSERT INTO `sys_paramter` VALUES ('387651921aee11ea9e8968f728cf81d1', '求职者', 'USER_ROLE', '1', 1, '2020-09-15 22:53:42', '1');
INSERT INTO `sys_paramter` VALUES ('387651921aee11ea9e8968f728cf81d2', '企业HR', 'USER_ROLE', '2', 1, '2020-09-15 22:54:13', '1');
INSERT INTO `sys_paramter` VALUES ('387651921aee11ea9e8968f728cf81d3', '学校就业负责人', 'USER_ROLE', '3', 1, '2020-09-15 22:54:19', '1');
INSERT INTO `sys_paramter` VALUES ('387651921aee11ea9e8968f728cf81d4', '系统管理员', 'USER_ROLE', '4', 1, '2020-09-20 17:32:01', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `SEX` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '9' COMMENT '性别、typecode=XB',
  `SFZ` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `LXDH` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `USER_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '888888' COMMENT '密码',
  `ROLE` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色',
  `ZCSJ` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `YXZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态（1有效、0无效、-1冻结）',
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `IDX_SYS_USER_USER_NAME`(`USER_NAME`) USING BTREE COMMENT '设置用户名为唯一索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('0127daeca7a04cbaaefc40eac5eb2307', '企业HR', '2', '621021021014521012', '15241254121', 'qyhr', '111111', '2', '2020-09-24 21:17:20', '1');
INSERT INTO `sys_user` VALUES ('7fcc27e168404b86b13009af4a8d9e4e', '王五', '1', '620502199502144521', '15241741214', 'ww', '111111', '3', '2020-09-20 22:18:18', '1');
INSERT INTO `sys_user` VALUES ('a12bf6601f144e50bb64889376fddeda', '系统管理员', '1', '622122197212141230', '15214123214', 'admin', '888888', '4', '2020-09-20 17:45:21', '1');
INSERT INTO `sys_user` VALUES ('de0ab6d16ea54e279d705151bfba1c34', '张三', '1', '620145214125412101', '15984521520', 'zs', '111111', '1', '2020-09-15 23:41:29', '1');
INSERT INTO `sys_user` VALUES ('f07450003b094c75be5253905fb1bd98', '李四', '2', '576756543545658768', '15214741221', 'ls', '111111', '2', '2020-09-15 21:37:17', '1');
INSERT INTO `sys_user` VALUES ('f92dba04695a43209edc59433f90da83', '求职者', '2', '654567654345654321', '13254125410', 'qzz', '111111', '1', '2020-09-21 11:41:38', '1');

-- ----------------------------
-- Table structure for tb_grjl
-- ----------------------------
DROP TABLE IF EXISTS `tb_grjl`;
CREATE TABLE `tb_grjl`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户主键',
  `CSRQ` date NULL DEFAULT NULL COMMENT '出生日期',
  `SG` int(3) NULL DEFAULT NULL COMMENT '身高',
  `MZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `ZZMM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌',
  `JTZZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `DZYX` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `BYYX` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业院校',
  `ZGXL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最高学历',
  `YXZW` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '意向职位',
  `SXZY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所学专业',
  `ZXKC` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '主修课程',
  `XXJL` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '学校经历',
  `HJQK` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '获奖情况',
  `ZWPJ` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '自我评价',
  `YXZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态 1：有效、0：无效',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_grjl
-- ----------------------------
INSERT INTO `tb_grjl` VALUES ('7d6b9ca0bac6486ea785385a1f3168d8', 'de0ab6d16ea54e279d705151bfba1c34', '2020-09-24', 111, '的沙发特然', '色胆如天赶尾人', '当地人托管人染色单体', '而台湾而一台', '认为热情它', '其而同期儿童', '热特瑞请二位啊', '我热特瑞退一万二', '1、的风格让他以为\r\n2、方故事的风格让他以为东\r\n3、让他以为让他的风格', '1、的风格让他以为\r\n2、方故事的风格让他以为东\r\n3、让他以为让他的风格', '1、的风格让他以为\r\n2、方故事的风格让他以为东\r\n3、让他以为让他的风格', '1、的风格让他以为\r\n2、方故事的风格让他以为东\r\n3、让他以为让他的风格\r\n4、的风格让他以为\r\n5、方故事的风格让他以为东\r\n6、让他以为让他的风格', '1');

-- ----------------------------
-- Table structure for tb_jltdjl
-- ----------------------------
DROP TABLE IF EXISTS `tb_jltdjl`;
CREATE TABLE `tb_jltdjl`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户主键',
  `DW_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位主键',
  `ZW_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位主键',
  `SPZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批状态 0：未审核、1：邀请面试、-1：拒绝面试',
  `SPSJ` datetime(0) NULL DEFAULT NULL COMMENT '审批时间',
  `SPYJ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `MSSJ` date NULL DEFAULT NULL COMMENT '面试时间',
  `MSDZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '面试地址',
  `LYZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '录用状态 0：未反馈、1：已录用、-1：拒绝录用',
  `LYSJ` datetime(0) NULL DEFAULT NULL COMMENT '录用时间',
  `YXZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态 1：有效、0：无效',
  `ZXYY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注销原因',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_jltdjl
-- ----------------------------
INSERT INTO `tb_jltdjl` VALUES ('4b9a366fc1034319992ef909ab8255ec', 'de0ab6d16ea54e279d705151bfba1c34', '84057e39daa2414f844cde28016f7b00', 'a08da3ab218a4e9ab957090d3166bd1d', '0', NULL, NULL, NULL, NULL, '0', NULL, '1', NULL);
INSERT INTO `tb_jltdjl` VALUES ('a839344452bc4ab492abd6f57c33aee4', 'de0ab6d16ea54e279d705151bfba1c34', '01eb7fab43d847bd96da6a72481ed2d3', '2a85bde8a9734bf28bdcac375d3c68bf', '0', NULL, NULL, NULL, NULL, '0', NULL, '1', NULL);
INSERT INTO `tb_jltdjl` VALUES ('ed4dde6e4b414194afce82aa476750ab', 'de0ab6d16ea54e279d705151bfba1c34', '01eb7fab43d847bd96da6a72481ed2d3', '0e5c9dfcb88a4089be9fc295e8b84199', '0', NULL, NULL, NULL, NULL, '0', NULL, '1', NULL);

-- ----------------------------
-- Table structure for tb_zpdw
-- ----------------------------
DROP TABLE IF EXISTS `tb_zpdw`;
CREATE TABLE `tb_zpdw`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户主键',
  `GSMC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `CLSJ` date NULL DEFAULT NULL COMMENT '成立时间',
  `FRDB` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '法人代表',
  `ZJDH` varchar(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '座机电话',
  `GSDZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司地址',
  `GSFL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司福利',
  `GSJJ` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '公司简介',
  `SHZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态 1：已审核、0：未审核',
  `SHYJ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见',
  `YXZT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_zpdw
-- ----------------------------
INSERT INTO `tb_zpdw` VALUES ('01eb7fab43d847bd96da6a72481ed2d3', 'f07450003b094c75be5253905fb1bd98', '装修公司', '2020-09-26', '郭海涛', '0931-8541231', '甘肃省兰州市城关区小西湖', '1、每年体检\r\n2、高温补贴\r\n3、每年旅游\r\n4、13薪水', '公司是一个非常棒的公司', '1', '审核通过', '1');
INSERT INTO `tb_zpdw` VALUES ('84057e39daa2414f844cde28016f7b00', 'f07450003b094c75be5253905fb1bd98', '北大青鸟', '2020-09-30', '房栋', '0931-8452141', '甘肃省兰州市安宁区', '建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言', '建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言建议投入而言', '1', '审核通过', '1');

-- ----------------------------
-- Table structure for tb_zpdw_zw
-- ----------------------------
DROP TABLE IF EXISTS `tb_zpdw_zw`;
CREATE TABLE `tb_zpdw_zw`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `DW_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '单位主键',
  `ZWMC` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位名称',
  `ZWZZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位职责',
  `ZWXLYQ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位学历要求',
  `GZJYYQ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作经验要求',
  `SBDZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上班地址',
  `YXZT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '有效状态',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_zpdw_zw
-- ----------------------------
INSERT INTO `tb_zpdw_zw` VALUES ('0e5c9dfcb88a4089be9fc295e8b84199', '01eb7fab43d847bd96da6a72481ed2d3', '运维工程师', '项目实施', '大专', '一年经验', '城关区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('2a85bde8a9734bf28bdcac375d3c68bf', '01eb7fab43d847bd96da6a72481ed2d3', 'java工程师', 'java开发', '大专', 'java基础扎实者优先', '城关区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('2f14211fa6c449358011a66e4d5786aa', '84057e39daa2414f844cde28016f7b00', '软件工程师', '项目的整体部署和运行', '本科及以上', '三年以上工作经验', '安宁区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('42b5cadbe5a440dc9c3e4f8fa4541222', '01eb7fab43d847bd96da6a72481ed2d3', 'java高级工程师', '懂得微服务', '博士', '10年以上工作经验', '安宁区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('544d55e264c74708909241f3132c38e1', '84057e39daa2414f844cde28016f7b00', 'java工程师', 'java研发', '大专', '两年以上', '安宁', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('a08da3ab218a4e9ab957090d3166bd1d', '84057e39daa2414f844cde28016f7b00', '运维工程师', '系统测试', '大专', '三年及以上', '城关区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('a8253ee94ae745469cead00e13e355f9', '84057e39daa2414f844cde28016f7b00', '会计', '整理公司账户', '大专', '两年以上', '天水', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('ce590ffc03eb4e08ad856e35fa0c6d6c', '01eb7fab43d847bd96da6a72481ed2d3', '项目经理', '搭建项目的整体框架', '大专及以上', '项目管理经验三年以上', '安宁区', '1');
INSERT INTO `tb_zpdw_zw` VALUES ('d1caad18b100425d9f57354a305fbb6c', '01eb7fab43d847bd96da6a72481ed2d3', '高级会计师', '整理公司账户', '本科', '三年以上', '城关区雁滩路', '1');

-- ----------------------------
-- Table structure for tb_zwtjjl
-- ----------------------------
DROP TABLE IF EXISTS `tb_zwtjjl`;
CREATE TABLE `tb_zwtjjl`  (
  `ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
  `DW_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单位主键',
  `ZW_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位主键',
  `TJ_USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推荐用户主键',
  `TX_USER_ID` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '推向有用户主键',
  `YXZT` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ' 有效状态 1：有效、0：无效',
  `SFTD` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '是否投递 1：已投递、0：未投递',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_zwtjjl
-- ----------------------------
INSERT INTO `tb_zwtjjl` VALUES ('46da3c5838d743cc93b9e299d642a200', '01eb7fab43d847bd96da6a72481ed2d3', '0e5c9dfcb88a4089be9fc295e8b84199', '7fcc27e168404b86b13009af4a8d9e4e', 'de0ab6d16ea54e279d705151bfba1c34', '1', '1');
INSERT INTO `tb_zwtjjl` VALUES ('7952e738ec404719a369fa06cabf47ad', '01eb7fab43d847bd96da6a72481ed2d3', 'd1caad18b100425d9f57354a305fbb6c', '7fcc27e168404b86b13009af4a8d9e4e', 'de0ab6d16ea54e279d705151bfba1c34', '1', '0');
INSERT INTO `tb_zwtjjl` VALUES ('bfe2c76a383b4baba4e11166f50ec397', '01eb7fab43d847bd96da6a72481ed2d3', '0e5c9dfcb88a4089be9fc295e8b84199', '7fcc27e168404b86b13009af4a8d9e4e', 'f92dba04695a43209edc59433f90da83', '1', '0');
INSERT INTO `tb_zwtjjl` VALUES ('fb563bd4c32643a3ae2f7c06a9158753', '84057e39daa2414f844cde28016f7b00', 'a08da3ab218a4e9ab957090d3166bd1d', '7fcc27e168404b86b13009af4a8d9e4e', 'f92dba04695a43209edc59433f90da83', '1', '0');

SET FOREIGN_KEY_CHECKS = 1;
