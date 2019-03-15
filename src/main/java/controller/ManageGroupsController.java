package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.dao.GroupDao;
import model.entity.Group;
import view.SceneManager;

import java.util.List;

public class ManageGroupsController {

  private GroupDao groupDao = GroupDao.getInstance();
  private SceneManager manager = SceneManager.getSceneManager();

  @FXML
  Button deleteGroupButton;

  @FXML
  Button changeGroupButton;

  @FXML
  Button newGroupButton;

  @FXML
  Button menuButton;

  @FXML
  ListView<String> groupList;

  public ManageGroupsController() { super(); }

  public void setup() {
    List<Group> groups = groupDao.getAllGroups();
    for (Group g: groups) {
      groupList.getItems().add(g.toString());
    }
  }

  public void doDeleteGroup(ActionEvent event) {
    int groupIndex = groupList.getSelectionModel().getSelectedIndex();
    Group group = groupDao.findGroupByIndex(groupIndex);
    System.out.println("Verwijder groep: " + group.getName());
    groupDao.deleteGroup(group);
    manager.showManageGroupsScene();
  }

  public void doChangeGroup(ActionEvent event) {
    int groupIndex = Math.max(groupList.getSelectionModel().getSelectedIndex(), 0);
    Group group = groupDao.findGroupByIndex(groupIndex);
    manager.showChangeGroupScene(group);
  }

  public void doCreateGroup(ActionEvent event) {
    manager.showNewGroupScene();
  }

  public void doMenu(ActionEvent event) {
    manager.showWelcomeScene();
  }
}
