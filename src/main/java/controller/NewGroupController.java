package controller;

import controller.shared.UpdateGroupController;
import javafx.event.ActionEvent;
import model.entity.Group;

public class NewGroupController extends UpdateGroupController {

  public void setup() {
    super.setup();
  }

  public void doCreateGroup(ActionEvent event) {
    group = new Group(nameField.getText(), teacher, course);
    groupDao.addGroup(group);
    manager.showManageGroupsScene();
  }
}
