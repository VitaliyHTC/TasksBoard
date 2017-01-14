package com.vitaliyhtc.tasksboard.controller;

import com.vitaliyhtc.tasksboard.converter.StringArrayToUserSortedSet;
import com.vitaliyhtc.tasksboard.model.*;
import com.vitaliyhtc.tasksboard.service.*;
import com.vitaliyhtc.tasksboard.validator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

@Controller
public class AdminController {

    @Autowired private UserService userService;
    @Autowired private UserValidator userValidator;
    @Autowired private UserUpdateValidator userUpdateValidator;
    @Autowired private GroupService groupService;
    @Autowired private GroupValidator groupValidator;
    @Autowired private GroupUpdateValidator groupUpdateValidator;
    @Autowired private BoardService boardService;
    @Autowired private BoardValidator boardValidator;
    @Autowired private BoardUpdateValidator boardUpdateValidator;
    @Autowired private BoardVisibilityService boardVisibilityService;
    @Autowired private TaskListService taskListService;
    @Autowired private TaskListValidator taskListValidator;
    @Autowired private TaskItemService taskItemService;
    @Autowired private TaskItemValidator taskItemValidator;
    @Autowired private StringArrayToUserSortedSet stringArrayToUserSortedSet;

    /*****************************************
     * USERS _crud ***************************
     *****************************************/
    private boolean isAdmin(Long userID){
        User userFindedById = userService.findById(userID);
        boolean isAdmin = false;
        for (Role role : userFindedById.getRoles()) {
            if("ROLE_ADMIN".equals(role.getName())){
                isAdmin = true;
            }
        }
        return isAdmin;
    }
    private boolean isAdmin(User user){
        boolean isAdmin = false;
        for (Role role : user.getRoles()) {
            if("ROLE_ADMIN".equals(role.getName())){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @RequestMapping(value = "admin/users", method = RequestMethod.GET)
    public String getUsers(Model model){
        List<User> usersList = userService.getAll();
        List<User> usersAdminsList = new ArrayList<>();
        List<User> usersUsersList = new ArrayList<>();
        for (User user : usersList) {
            if(isAdmin(user)){
                usersAdminsList.add(user);
            }else{
                usersUsersList.add(user);
            }
        }
        model.addAttribute("usersAdminsList", usersAdminsList);
        model.addAttribute("usersUsersList", usersUsersList);
        return "adminUsers";
    }

    @RequestMapping(value = "admin/addnewuser", method = RequestMethod.GET)
    public String addNewUser(Model model){
        User userForm = new User();
        userForm.setEnabled(true);
        userForm.setAccountNonLocked(true);
        model.addAttribute("userForm", userForm);
        return "adminUserNew";
    }

    @RequestMapping(value = "admin/addnewuser", method = RequestMethod.POST)
    public String registerNewUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model){
        userValidator.validate(userForm, bindingResult);
        if(bindingResult.hasErrors()){
            return "adminUserNew";
        }
        userService.save(userForm);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "admin/editexistinguser", method = RequestMethod.GET)
    public String editExistingUser(
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            Model model){
        if(isAdmin(itemIDtoEdit)){
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg", "You haven't access to edit this User.");
            model.addAttribute("form_disabled", "disabled");
        }else{
            model.addAttribute("form_disabled", "");
            User userToEdit = new User();
            User userFindedById = userService.findById(itemIDtoEdit);
            userToEdit.setUsername(userFindedById.getUsername());
            userToEdit.setEmail(userFindedById.getEmail());
            userToEdit.setFirstname(userFindedById.getFirstname());
            userToEdit.setLastname(userFindedById.getLastname());
            userToEdit.setEnabled(userFindedById.isEnabled());
            userToEdit.setAccountNonLocked(userFindedById.isAccountNonLocked());
            model.addAttribute("userForm", userToEdit);
            model.addAttribute("itemIDtoEdit", itemIDtoEdit);
        }
        return "adminUserEdit";
    }

    @RequestMapping(value = "admin/editexistinguser", method = RequestMethod.POST)
    public String updateExistingUser(
            @ModelAttribute("userForm") User userForm,
            BindingResult bindingResult,
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            Model model){
        if(isAdmin(itemIDtoEdit)){
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg", "You haven't access to edit this User.");
            model.addAttribute("form_disabled", "disabled");
            return "adminUserEdit";
        }else{
            model.addAttribute("form_disabled", "");
            userForm.setId(itemIDtoEdit);
            userUpdateValidator.validate(userForm, bindingResult);
            if(bindingResult.hasErrors()){
                model.addAttribute("itemIDtoEdit", itemIDtoEdit);
                return "adminUserEdit";
            }
            User userToUpdate = userService.findById(itemIDtoEdit);
            userToUpdate.setUsername(userForm.getUsername());
            userToUpdate.setEmail(userForm.getEmail());
            userToUpdate.setFirstname(userForm.getFirstname());
            userToUpdate.setLastname(userForm.getLastname());
            userToUpdate.setEnabled(userForm.isEnabled());
            userToUpdate.setAccountNonLocked(userForm.isAccountNonLocked());
            Boolean isPasswordChanged = false;
            if((!userForm.getPassword().isEmpty() || !userForm.getConfirmPassword().isEmpty())
                    && (userForm.getPassword().equals(userForm.getConfirmPassword()))){
                userToUpdate.setPassword(userForm.getPassword());
                userToUpdate.setConfirmPassword(userForm.getConfirmPassword());
                isPasswordChanged = true;
            }
            userService.updateUser(userToUpdate, isPasswordChanged);
            return "redirect:/admin/users";
        }
    }


    @RequestMapping(value = "admin/deleteuser", method = RequestMethod.GET)
    public String deleteUser(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            Model model){
        if(isAdmin(itemIDtoDelete)) {
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg", "You haven't access to delete this User.");
            model.addAttribute("form_disabled", "disabled");
        }else{
            User userFindedById = userService.findById(itemIDtoDelete);
            if(userFindedById.getGroups().size()>0 || userFindedById.getBoards().size()>0){
                model.addAttribute("forbidden", true);
                model.addAttribute("errormsg",
                        "User is connected to some groups and/or boards.\n" +
                                "May be, disabling or locking of account will be better idea.<br>" +
                                "If you agreed with it - you can <a href=\"/admin/editexistinguser?itemIDtoEdit=" +
                                itemIDtoDelete + "\">disable or lock user account on edit page.</a><br>" +
                                "If you need to delete user: first - you need to remove connection between user and" +
                                " groups, boards. Then delete user. But, if user posted some info to boards -" +
                                " you can't delete it. Deleting will break data consistency.");
                model.addAttribute("form_disabled", "disabled");
            }else{
                model.addAttribute("form_disabled", "");
                User userToDelete = new User();
                userToDelete.setUsername(userFindedById.getUsername());
                userToDelete.setEmail(userFindedById.getEmail());
                userToDelete.setFirstname(userFindedById.getFirstname());
                userToDelete.setLastname(userFindedById.getLastname());
                model.addAttribute("userToDelete", userToDelete);
                model.addAttribute("itemIDtoDelete", itemIDtoDelete);
            }
        }
        return "adminUserDelete";
    }

    @RequestMapping(value = "admin/deleteuser", method = RequestMethod.POST)
    public String deleteUser(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "radioconfirm") String radioconfirm,
            @RequestParam(value = "checkboxconfirm", required = false) String checkboxconfirm,
            Model model){
        if(isAdmin(itemIDtoDelete)) {
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg", "You haven't access to delete this User.");
            model.addAttribute("form_disabled", "disabled");
            return "adminUserDelete";
        }else{
            model.addAttribute("form_disabled", "");
            User userFindedById = userService.findById(itemIDtoDelete);
            if(userFindedById.getGroups().size()>0 || userFindedById.getBoards().size()>0){
                return "redirect:/admin/deleteuser?itemIDtoDelete="+itemIDtoDelete;
            }
            if("checkbox_yes".equals(checkboxconfirm) && "radio_yes".equals(radioconfirm)){
                userService.removeUser(userService.findById(itemIDtoDelete));
            }else{
                model.addAttribute("forbidden", true);
                model.addAttribute("errormsg",
                        "User not deleted. Verify radiobuttons selection, check checkbox and try again.");
                User userToDelete = new User();
                userToDelete.setUsername(userFindedById.getUsername());
                userToDelete.setEmail(userFindedById.getEmail());
                userToDelete.setFirstname(userFindedById.getFirstname());
                userToDelete.setLastname(userFindedById.getLastname());
                model.addAttribute("userToDelete", userToDelete);
                model.addAttribute("itemIDtoDelete", itemIDtoDelete);
                return "adminUserDelete";
            }
            return "redirect:/admin/users";
        }
    }



    /*****************************************
     * groups _crud **************************
     *****************************************/

    @RequestMapping(value = "admin/groups", method = RequestMethod.GET)
    public String getGroups(Model model){
        model.addAttribute("groupsList", groupService.getAll());
        return "adminGroups";
    }

    @RequestMapping(value = "admin/addnewgroup", method = RequestMethod.GET)
    public String addNewGroup(Model model){
        model.addAttribute("groupForm", new Group());
        model.addAttribute("usersList", userService.getAll());
        return "adminGroupNew";
    }

    @RequestMapping(value = "admin/addnewgroup", method = RequestMethod.POST)
    public String registerNewGroup(
            @ModelAttribute("groupForm") Group groupForm,
            @RequestParam(value = "users", required = false) String[] usersArray,
            BindingResult bindingResult,
            Model model){
        groupValidator.validate(groupForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("usersList", userService.getAll());
            return "adminGroupNew";
        }
        groupForm.getUsers().clear();
        if(usersArray!=null){
            groupForm.setUsers(stringArrayToUserSortedSet.convert(usersArray));
        }
        groupService.addGroup(groupForm);
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "admin/editexistinggroup", method = RequestMethod.GET)
    public String editExistingGroup(
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            Model model){
        model.addAttribute("groupForm", groupService.findById(itemIDtoEdit));
        model.addAttribute("usersList", userService.getAll());
        model.addAttribute("itemIDtoEdit", itemIDtoEdit);
        return "adminGroupEdit";
    }

    @RequestMapping(value = "admin/editexistinggroup", method = RequestMethod.POST)
    public String updateExistingGroup(
            @ModelAttribute("groupForm") Group groupForm,
            @RequestParam(value = "users", required = false) String[] usersArray,
            BindingResult bindingResult,
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            Model model){
        groupForm.setId(itemIDtoEdit);

        groupUpdateValidator.validate(groupForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("usersList", userService.getAll());
            model.addAttribute("itemIDtoEdit", itemIDtoEdit);
            return "adminGroupEdit";
        }
        Group groupToUpdate = groupService.findById(itemIDtoEdit);
        groupToUpdate.setGroupName(groupForm.getGroupName());
        groupToUpdate.setGroupDescription(groupForm.getGroupDescription());
        if(usersArray!=null){
            groupToUpdate.setUsers(stringArrayToUserSortedSet.convert(usersArray));
        }else{
            groupToUpdate.setUsers(new TreeSet<User>());
        }
        groupService.updateGroup(groupToUpdate);
        return "redirect:/admin/groups";
    }

    @RequestMapping(value = "admin/deletegroup", method = RequestMethod.GET)
    public String deleteGroup(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            Model model){
        Group groupToDelete = groupService.findById(itemIDtoDelete);
        if(!groupToDelete.getBoards().isEmpty()){
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg", "You can't delete this group. Some boards connected to group.");
            model.addAttribute("form_disabled", "disabled");
        }else{
            model.addAttribute("form_disabled", "");
        }
        model.addAttribute("groupToDelete", groupToDelete);
        model.addAttribute("itemIDtoDelete", itemIDtoDelete);
        return "adminGroupDelete";
    }

    @RequestMapping(value = "admin/deletegroup", method = RequestMethod.POST)
    public String deleteGroup(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "radioconfirm") String radioconfirm,
            @RequestParam(value = "checkboxconfirm", required = false) String checkboxconfirm,
            Model model){
        if("checkbox_yes".equals(checkboxconfirm) && "radio_yes".equals(radioconfirm)){
            groupService.removeGroup(groupService.findById(itemIDtoDelete));
        }else{
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg",
                    "Group not deleted. Verify radiobuttons selection, check checkbox and try again.");
            model.addAttribute("groupToDelete", groupService.findById(itemIDtoDelete));
            model.addAttribute("itemIDtoDelete", itemIDtoDelete);
            return "adminGroupDelete";
        }
        return "redirect:/admin/groups";
    }



    /*****************************************
     * boards _crud **************************
     *****************************************/
    @RequestMapping(value = "admin/boards", method = RequestMethod.GET)
    public String getBoards(Model model){
        model.addAttribute("boardsList", boardService.getAll());
        return "adminBoards";
    }

    @RequestMapping(value = "admin/addnewboard", method = RequestMethod.GET)
    public String addNewBoard(Model model){
        model.addAttribute("boardForm", new Board());
        model.addAttribute("visibilityList", boardVisibilityService.getAll());
        model.addAttribute("usersList", userService.getAll());
        List<Group> groupsList = new ArrayList<>();
        Group nullGroup = new Group();
        nullGroup.setId(0L);
        nullGroup.setGroupName("No selected groups");
        groupsList.add(nullGroup);
        groupsList.addAll(groupService.getAll());
        model.addAttribute("groupsList", groupsList);
        return "adminBoardNew";
    }

    @RequestMapping(value = "admin/addnewboard", method = RequestMethod.POST)
    public String registerNewBoard(
            @ModelAttribute("boardForm") Board boardForm,
            @RequestParam(value = "users", required = false) String[] usersArray,
            BindingResult bindingResult,
            Model model){
        boardValidator.validate(boardForm, bindingResult);
        if(bindingResult.hasErrors()){
            Long visibilitySelectedID = null;
            if(boardForm.getBoardVisibility()!=null){
                visibilitySelectedID = boardForm.getBoardVisibility().getId();
            }
            Long groupSelectedID = null;
            if(boardForm.getGroup()!=null){
                groupSelectedID = boardForm.getGroup().getId();
            }
            List<Group> groupsList = new ArrayList<>();
            Group nullGroup = new Group();
            nullGroup.setId(0L);
            nullGroup.setGroupName("No selected groups");
            groupsList.add(nullGroup);
            groupsList.addAll(groupService.getAll());
            model.addAttribute("visibilityList", boardVisibilityService.getAll());
            model.addAttribute("visibilitySelectedID", visibilitySelectedID);
            model.addAttribute("usersList", userService.getAll());
            model.addAttribute("groupsList", groupsList);
            model.addAttribute("groupSelectedID", groupSelectedID);
            return "adminBoardNew";
        }
        boardForm.getUsers().clear();
        if(usersArray!=null){
            boardForm.setUsers(stringArrayToUserSortedSet.convert(usersArray));
        }
        boardService.addBoard(boardForm);
        return "redirect:/admin/boards";
    }

    @RequestMapping(value = "admin/editexistingboard", method = RequestMethod.GET)
    public String editExistingBoard(
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            Model model){
        Board boardFindedById = boardService.findById(itemIDtoEdit);
        List<Group> groupsList = new ArrayList<>();
        Long groupSelectedID = null;
        if(boardFindedById.getGroup()!=null){
            groupSelectedID = boardFindedById.getGroup().getId();
        }
        Group nullGroup = new Group();
        nullGroup.setId(0L);
        nullGroup.setGroupName("No selected groups");
        groupsList.add(nullGroup);
        groupsList.addAll(groupService.getAll());
        model.addAttribute("visibilityList", boardVisibilityService.getAll());
        model.addAttribute("visibilitySelectedID", boardFindedById.getBoardVisibility().getId());
        model.addAttribute("usersList", userService.getAll());
        model.addAttribute("groupsList", groupsList);
        model.addAttribute("boardForm", boardFindedById);
        model.addAttribute("groupSelectedID", groupSelectedID);
        model.addAttribute("itemIDtoEdit", itemIDtoEdit);
        model.addAttribute("redirectPage", redirectPage);
        return "adminBoardEdit";
    }

    @RequestMapping(value = "admin/editexistingboard", method = RequestMethod.POST)
    public String updateExistingBoard(
            @ModelAttribute("boardForm") Board boardForm,
            @RequestParam(value = "users", required = false) String[] usersArray,
            BindingResult bindingResult,
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            Model model){
        boardForm.setId(itemIDtoEdit);
        boardUpdateValidator.validate(boardForm, bindingResult);
        if(bindingResult.hasErrors()){
            Long visibilitySelectedID = null;
            if(boardForm.getBoardVisibility()!=null){
                visibilitySelectedID = boardForm.getBoardVisibility().getId();
            }
            Long groupSelectedID = null;
            if(boardForm.getGroup()!=null){
                groupSelectedID = boardForm.getGroup().getId();
            }
            List<Group> groupsList = new ArrayList<>();
            Group nullGroup = new Group();
            nullGroup.setId(0L);
            nullGroup.setGroupName("No selected groups");
            groupsList.add(nullGroup);
            groupsList.addAll(groupService.getAll());
            model.addAttribute("visibilityList", boardVisibilityService.getAll());
            model.addAttribute("visibilitySelectedID", visibilitySelectedID);
            model.addAttribute("usersList", userService.getAll());
            model.addAttribute("groupsList", groupsList);
            model.addAttribute("groupSelectedID", groupSelectedID);
            model.addAttribute("itemIDtoEdit", itemIDtoEdit);
            model.addAttribute("redirectPage", redirectPage);
            return "adminBoardEdit";
        }
        Board boardToUpdate = boardService.findById(itemIDtoEdit);
        boardToUpdate.setBoardName(boardForm.getBoardName());
        boardToUpdate.setBoardDescription(boardForm.getBoardDescription());
        boardToUpdate.setBoardVisibility(boardForm.getBoardVisibility());
        if(usersArray!=null){
            boardToUpdate.setUsers(stringArrayToUserSortedSet.convert(usersArray));
        }else{
            boardToUpdate.setUsers(new TreeSet<User>());
        }
        boardToUpdate.setGroup(boardForm.getGroup());
        boardService.updateBoard(boardToUpdate);
        if(redirectPage!=null){
            if("board".equals(redirectPage)){
                return "redirect:/admin/board?boardID="+itemIDtoEdit;
            }
        }
        return "redirect:/admin/boards";
    }

    @RequestMapping(value = "admin/deleteboard", method = RequestMethod.GET)
    public String deleteBoard(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            Model model){
        model.addAttribute("boardToDelete", boardService.findById(itemIDtoDelete));
        model.addAttribute("itemIDtoDelete", itemIDtoDelete);
        return "adminBoardDelete";
    }

    @RequestMapping(value = "admin/deleteboard", method = RequestMethod.POST)
    public String deleteBoard(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "radioconfirm") String radioconfirm,
            @RequestParam(value = "checkboxconfirm", required = false) String checkboxconfirm,
            Model model){
        if("checkbox_yes".equals(checkboxconfirm) && "radio_yes".equals(radioconfirm)){
            boardService.removeBoard(boardService.findById(itemIDtoDelete));
        }else{
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg",
                    "Board not deleted. Verify radiobuttons selection, check checkbox and try again.");
            model.addAttribute("boardToDelete", boardService.findById(itemIDtoDelete));
            model.addAttribute("itemIDtoDelete", itemIDtoDelete);
            return "adminBoardDelete";
        }
        return "redirect:/admin/boards";
    }



    /*****************************************
     * view board, board objects crud ********
     *****************************************/

    @RequestMapping(value = "admin/board", method = RequestMethod.GET)
    public String viewBoard(
            @RequestParam(value = "boardID") Long boardID,
            Model model){
        model.addAttribute("visibilityList", boardVisibilityService.getAll());
        model.addAttribute("board", boardService.findById(boardID));
        return "adminBoard";
    }

    @RequestMapping(value = "admin/addtasklist", method = RequestMethod.GET)
    public String addNewTaskList(
            @RequestParam(value = "boardID") Long boardID,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            Model model){
        model.addAttribute("taskListForm", new TaskList());
        model.addAttribute("boardID", boardID);
        model.addAttribute("redirectPage", redirectPage);
        return "adminTaskListNew";
    }

    @RequestMapping(value = "admin/addnewtasklist", method = RequestMethod.POST)
    public String registerNewTaskList(
            @ModelAttribute("taskListForm") TaskList taskListForm,
            @RequestParam(value = "boardID") Long boardID,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            BindingResult bindingResult,
            Model model){
        taskListValidator.validate(taskListForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("boardID", boardID);
            model.addAttribute("redirectPage", redirectPage);
            return "adminTaskListNew";
        }
        taskListForm.setBoard(boardService.findById(boardID));
        taskListService.addTaskList(taskListForm);
        if(redirectPage!=null){
            if("board".equals(redirectPage)){
                return "redirect:/admin/board?boardID="+boardID;
            }
        }
        return "redirect:/admin/boards";
    }

    @RequestMapping(value = "admin/editexistingtasklist", method = RequestMethod.GET)
    public String editExistingTaskList(
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            Model model){
        model.addAttribute("itemIDtoEdit", itemIDtoEdit);
        model.addAttribute("boardID", taskListService.findById(itemIDtoEdit).getBoard().getId());
        model.addAttribute("redirectPage", redirectPage);
        model.addAttribute("taskListForm", taskListService.findById(itemIDtoEdit));
        return "adminTaskListEdit";
    }

    @RequestMapping(value = "admin/editexistingtasklist", method = RequestMethod.POST)
    public String updateExistingTaskList(
            @ModelAttribute("taskListForm") TaskList taskListForm,
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            BindingResult bindingResult,
            Model model){
        taskListValidator.validate(taskListForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("itemIDtoEdit", itemIDtoEdit);
            model.addAttribute("boardID", taskListService.findById(itemIDtoEdit).getBoard().getId());
            model.addAttribute("redirectPage", redirectPage);
            return "adminTaskListEdit";
        }
        TaskList taskListToUpdate = taskListService.findById(itemIDtoEdit);
        taskListToUpdate.setListName(taskListForm.getListName());
        taskListToUpdate.setListDescription(taskListForm.getListDescription());
        taskListToUpdate.setItemsLimit(taskListForm.getItemsLimit());
        taskListService.updateTaskList(taskListToUpdate);
        if(redirectPage!=null){
            if("board".equals(redirectPage)){
                return "redirect:/admin/board?boardID="+taskListToUpdate.getBoard().getId();
            }
        }
        return "redirect:/admin/boards";
    }

    @RequestMapping(value = "admin/deletetasklist", method = RequestMethod.GET)
    public String deleteTaskList(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            Model model){
        model.addAttribute("taskListToDelete", taskListService.findById(itemIDtoDelete));
        model.addAttribute("redirectPage", redirectPage);
        model.addAttribute("itemIDtoDelete", itemIDtoDelete);
        return "adminTaskListDelete";
    }

    @RequestMapping(value = "admin/deletetasklist", method = RequestMethod.POST)
    public String deleteTaskList(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "redirectPage", required = false) String redirectPage,
            @RequestParam(value = "radioconfirm") String radioconfirm,
            @RequestParam(value = "checkboxconfirm", required = false) String checkboxconfirm,
            Model model){
        Long boardID;
        if("checkbox_yes".equals(checkboxconfirm) && "radio_yes".equals(radioconfirm)){
            boardID = taskListService.findById(itemIDtoDelete).getBoard().getId();
            taskListService.removeTaskList(taskListService.findById(itemIDtoDelete));
        }else{
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg",
                    "TaskList not deleted. Verify radiobuttons selection, check checkbox and try again.");
            model.addAttribute("taskListToDelete", taskListService.findById(itemIDtoDelete));
            model.addAttribute("itemIDtoDelete", itemIDtoDelete);
            if(redirectPage!=null){
                model.addAttribute("redirectPage", redirectPage);
            }
            return "adminTaskListDelete";
        }
        if(redirectPage!=null){
            if("board".equals(redirectPage)){
                return "redirect:/admin/board?boardID="+boardID;
            }
        }
        return "redirect:/admin/boards";
    }



    /*****************************************
     * board >>> taskItem's crud *************
     *****************************************/

    @RequestMapping(value = "admin/addtaskitem", method = RequestMethod.GET)
    public String addNewTaskItem(
            @RequestParam(value = "boardID") Long boardID,
            @RequestParam(value = "taskListID") Long taskListID,
            Model model){
        model.addAttribute("taskItemForm", new TaskItem());
        model.addAttribute("boardID", boardID);
        model.addAttribute("taskListID", taskListID);
        return "adminTaskItemNew";
    }

    @RequestMapping(value = "admin/addnewtaskitem", method = RequestMethod.POST)
    public String registerNewTaskItem(
            @ModelAttribute("taskItemForm") TaskItem taskItemForm,
            @RequestParam(value = "boardID") Long boardID,
            @RequestParam(value = "taskListID") Long taskListID,
            BindingResult bindingResult,
            Model model){
        taskItemValidator.validate(taskItemForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("boardID", boardID);
            model.addAttribute("taskListID", taskListID);
            return "adminTaskItemNew";
        }
        taskItemForm.setTaskList(taskListService.findById(taskListID));
        taskItemForm.setCreationDate(new Date());
        taskItemForm.setTaskItemOrder(0);
        taskItemService.addTaskItem(taskItemForm);
        return "redirect:/admin/board?boardID="+boardID;
    }

    @RequestMapping(value = "admin/editexistingtaskitem", method = RequestMethod.GET)
    public String editExistingTaskItem(
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            Model model){
        model.addAttribute("itemIDtoEdit", itemIDtoEdit);
        model.addAttribute("boardID", taskItemService.findById(itemIDtoEdit).getTaskList().getBoard().getId());
        model.addAttribute("taskItemForm", taskItemService.findById(itemIDtoEdit));
        return "adminTaskItemEdit";
    }

    @RequestMapping(value = "admin/editexistingtaskitem", method = RequestMethod.POST)
    public String updateExistingTaskItem(
            @ModelAttribute("taskItemForm") TaskItem taskItemForm,
            @RequestParam(value = "itemIDtoEdit") Long itemIDtoEdit,
            BindingResult bindingResult,
            Model model){
        taskItemValidator.validate(taskItemForm, bindingResult);
        if(bindingResult.hasErrors()){
            model.addAttribute("itemIDtoEdit", itemIDtoEdit);
            model.addAttribute("boardID", taskListService.findById(itemIDtoEdit).getBoard().getId());
            return "adminTaskItemEdit";
        }
        TaskItem taskItemToUpdate = taskItemService.findById(itemIDtoEdit);
        taskItemToUpdate.setTaskName(taskItemForm.getTaskName());
        taskItemToUpdate.setTaskDescription(taskItemForm.getTaskDescription());
        taskItemService.updateTaskItem(taskItemToUpdate);
        return "redirect:/admin/board?boardID="+taskItemToUpdate.getTaskList().getBoard().getId();
    }

    @RequestMapping(value = "admin/deletetaskitem", method = RequestMethod.GET)
    public String deleteTaskItem(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            Model model){
        model.addAttribute("taskItemToDelete", taskItemService.findById(itemIDtoDelete));
        model.addAttribute("itemIDtoDelete", itemIDtoDelete);
        model.addAttribute("boardID", taskItemService.findById(itemIDtoDelete).getTaskList().getBoard().getId());
        return "adminTaskItemDelete";
    }

    @RequestMapping(value = "admin/deletetaskitem", method = RequestMethod.POST)
    public String deleteTaskList(
            @RequestParam(value = "itemIDtoDelete") Long itemIDtoDelete,
            @RequestParam(value = "radioconfirm") String radioconfirm,
            @RequestParam(value = "checkboxconfirm", required = false) String checkboxconfirm,
            Model model){
        Long boardID;
        if("checkbox_yes".equals(checkboxconfirm) && "radio_yes".equals(radioconfirm)){
            boardID = taskItemService.findById(itemIDtoDelete).getTaskList().getBoard().getId();
            taskItemService.removeTaskItem(taskItemService.findById(itemIDtoDelete));
        }else{
            model.addAttribute("forbidden", true);
            model.addAttribute("errormsg",
                    "TaskItem not deleted. Verify radiobuttons selection, check checkbox and try again.");
            model.addAttribute("taskItemToDelete", taskItemService.findById(itemIDtoDelete));
            model.addAttribute("itemIDtoDelete", itemIDtoDelete);
            model.addAttribute("boardID", taskItemService.findById(itemIDtoDelete).getTaskList().getBoard().getId());
            return "adminTaskItemDelete";
        }
        return "redirect:/admin/board?boardID="+boardID;
    }



    /*****************************************
     * file upload and other *****************
     *****************************************/

    @RequestMapping(value = "admin/uploadFileView", method = RequestMethod.GET)
    public String uploadFileView(
            //@RequestParam(value = "boardID") Long boardID,
            Model model){
        return "adminFileUpload";
    }

    //@PreAuthorize("permitAll")
    @RequestMapping(value = "admin/uploadFile", method = RequestMethod.POST)
    public String submitFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        return "adminFileUpload";
    }

    @RequestMapping(value = "admin/uploadMultiFile", method = RequestMethod.POST)
    public String submit(@RequestParam("files") MultipartFile[] files, Model model) {
        model.addAttribute("files", files);
        return "adminFileUpload";
    }

}
