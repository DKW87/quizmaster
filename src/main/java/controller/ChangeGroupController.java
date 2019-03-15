package controller;

import controller.shared.UpdateGroupController;
import javafx.event.ActionEvent;
import model.entity.Group;

public class ChangeGroupController extends UpdateGroupController {

  public void setup(Group group) {
    super.setup(group);
  }

  public void doChangeGroup(ActionEvent event) {
    group.setName(nameField.getText());
    group.setCourse(course);
    group.setTeacher(teacher);
    manager.showManageGroupsScene();
  }
}
