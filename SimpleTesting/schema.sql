CREATE DATABASE IF NOT EXISTS `simpletesting` CHARACTER SET utf8 COLLATE utf8_general_ci;


INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('1', 'красный');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('2', 'черный');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('3', 'белый');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('4', 'желтый');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('5', 'синий');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('6', 'зеленый');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('7', 'голубой');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('8', 'розовый');
INSERT INTO `simpletesting`.`answers` (`id`, `answer`) VALUES ('9', 'серый');

INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('1', 'Какой цвет солнца?', '4');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('2', 'Какой цвет неба в хорошую погоду?', '7');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('3', 'Какой цвет травы?', '6');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('4', 'Какой цвет песка?', '4');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('5', 'Какой цвет цемента?', '9');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('6', 'Какой цвет лепестков ромашки?', '3');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('7', 'Какой цвет листьев на деревьях весной?', '6');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('8', 'Какой цвет листьев на деревьях осенью?', '4');
INSERT INTO `simpletesting`.`questions` (`id`, `question`, `answer_id`) VALUES ('9', 'Какой цвет неба ночью?', '2');