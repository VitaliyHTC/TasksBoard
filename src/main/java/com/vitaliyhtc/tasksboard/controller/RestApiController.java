package com.vitaliyhtc.tasksboard.controller;

import com.monitorjbl.json.JsonResult;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.Match;
import com.vitaliyhtc.tasksboard.model.Board;
import com.vitaliyhtc.tasksboard.model.User;
import com.vitaliyhtc.tasksboard.service.BoardService;
import com.vitaliyhtc.tasksboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApiController {
    
    @Autowired
    BoardService boardService;

    @Autowired
    UserService userService;









    /*
    //-------------------Retrieve All Boards that user can edit --------------------------------------
    @RequestMapping(value = {"/api/boardsCanEdit", "/index/api/boardsCanEdit"}, method = RequestMethod.GET)
    public ResponseEntity<List<Board>> registerNewUser() {
        List<Board> boards = boardService.findByUser(getUserFromContext());
        if(boards.isEmpty()){
            return new ResponseEntity<List<Board>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
    }
    */















    private User getUserFromContext(){
        org.springframework.security.core.userdetails.User userDetails =
                (org.springframework.security.core.userdetails.User)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(userDetails.getUsername());
    }
    


    // multiple values ===> @RequestMapping(value={"", "/", "welcome"})

    //-------------------Retrieve All Boards that user can edit --------------------------------------
    @RequestMapping(value = {"/api/boardsCanEdit", "/index/api/boardsCanEdit"}, method = RequestMethod.GET)
    public ResponseEntity<List<Board>> getBoardsCanEdit() {
        List<Board> boards = boardService.findByUser(getUserFromContext());
        if(boards.isEmpty()){
            return new ResponseEntity<List<Board>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
    }

    //-------------------Retrieve All Boards that user can edit --------------------------------------
    @RequestMapping(value = {"/api/boardsCanViewOnly", "/index/api/boardsCanViewOnly"}, method = RequestMethod.GET)
    public ResponseEntity<List<Board>> getBoardsCanViewOnly() {
        List<Board> boards = boardService.findViewOnlyByUser(getUserFromContext());
        if(boards.isEmpty()){
            return new ResponseEntity<List<Board>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
    }


    //-------------------Retrieve Single Board--------------------------------------------------------
    @RequestMapping(value = {"/api/board/{id}","/index/api/board/{id}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> getBoard(@PathVariable("id") long id) {
        System.out.println("Fetching Board with id " + id);
        Board board = boardService.findById(id);
        if (board == null) {
            System.out.println("Board with id " + id + " not found");
            return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Board>(board, HttpStatus.OK);
    }










    private JsonResult json = JsonResult.instance();

    @RequestMapping(method = RequestMethod.GET, value = "/api/boardsIncludeFields")
    @ResponseBody
    public List<Board> getTestObject(@RequestParam(value = "includeFields") String... includeFields) {
        List<Board> list = boardService.getAll();

        return json.use(JsonView.with(list)
                .onClass(Board.class, Match.match()
                        .exclude("*")
                        .include(includeFields)))
                .returnValue();
    }


































    //-------------------Retrieve admin access test--------------------------------------------------------
    @RequestMapping(value = "/api/test_na/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAccessTestNA(@PathVariable("id") long id) {
        System.out.println("UserAccessTest w/o annotation id= " + id);
        String result = "UserAccessTest w/o annotation id= " + id;
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    //-------------------Retrieve admin access test--------------------------------------------------------
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @RequestMapping(value = "/api/test_user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAccessTestUser(@PathVariable("id") long id) {
        System.out.println("UserAccessTest with ROLE_USER annotation id= " + id);
        String result = "UserAccessTest with ROLE_USER annotation id= " + id;
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    //-------------------Retrieve admin access test--------------------------------------------------------
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/api/test_admin/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAccessTestAdmin(@PathVariable("id") long id) {
        System.out.println("UserAccessTest with ROLE_ADMIN annotation id= " + id);
        String result = "UserAccessTest with ROLE_ADMIN annotation id= " + id;
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }

    //-------------------Retrieve admin access test--------------------------------------------------------
    @RequestMapping(value = "/api/admin/test/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAdminAccessTest(@PathVariable("id") long id) {
        System.out.println("AdminAccessTest id= " + id);
        String result = "AdminAccessTest id= " + id;
        return new ResponseEntity<String>(result, HttpStatus.OK);
    }


    /*
    //-------------------Create a Board--------------------------------------------------------
    @RequestMapping(value = "/api/board/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody Board board, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Board " + board.getBoardName());

        if (boardService.isBoardExist(board)) {
            System.out.println("A Board with name " + board.getBoardName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        boardService.addBoard(board);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/board/{id}").buildAndExpand(board.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    */

    /*
    //------------------- Update a Board --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Board> updateUser(@PathVariable("id") long id, @RequestBody Board user) {
        System.out.println("Updating Board " + id);

        Board currentUser = userService.findById(id);

        if (currentUser==null) {
            System.out.println("Board with id " + id + " not found");
            return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);
        return new ResponseEntity<Board>(currentUser, HttpStatus.OK);
    }

    //------------------- Delete a Board --------------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Board> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting Board with id " + id);

        Board user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. Board with id " + id + " not found");
            return new ResponseEntity<Board>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUserById(id);
        return new ResponseEntity<Board>(HttpStatus.NO_CONTENT);
    }


    //------------------- Delete All Users --------------------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Board> deleteAllUsers() {
        System.out.println("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<Board>(HttpStatus.NO_CONTENT);
    }
    */

}
