package cn.darkjrong.mybatis.generator.common.pojo.dto;


import cn.darkjrong.mybatis.generator.controller.base.BaseFxController;
import javafx.scene.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewHolder {
    private Node node;
    private BaseFxController controller;

}
