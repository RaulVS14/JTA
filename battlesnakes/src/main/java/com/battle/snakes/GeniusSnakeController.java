package com.battle.snakes;

import com.battle.snakes.game.*;
import com.battle.snakes.util.SnakeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/genius")
public class GeniusSnakeController extends BaseController {

  @RequestMapping(value = "/start", method = RequestMethod.POST, produces = "application/json")
  public StartResponse start(@RequestBody StartRequest request) {

    log.info(request.toString());

    return StartResponse.builder()
      .color("#188936")
      .headType(HeadType.EVIL.getValue())
      .tailType(TailType.BOLT.getValue())
      .build();
  }

  @RequestMapping(value = "/end", method = RequestMethod.POST)
  public Object end(@RequestBody EndRequest request) {

    log.info(request.toString());

    return new HashMap<String, Object>();
  }

  @RequestMapping(value = "/move", method = RequestMethod.POST, produces = "application/json")
  public MoveResponse move(@RequestBody MoveRequest request) {

    log.info(request.toString());

    List<MoveType> allowedMoves = SnakeUtil.getAllowedMoves(request);
    List<Coordinate> body = request.getYou().getBody();
    Board board = request.getBoard();
    Coordinate head = body.get(0);


    List<Coordinate> food = request.getBoard().getFood();

    List<Coordinate> reachableFood = new ArrayList<>();

    for (Coordinate coordinate : food) {
      if (SnakeUtil.hasPath(board, body, head, coordinate)) {
        reachableFood.add(coordinate);
      }
    }

    Coordinate nearest = SnakeUtil.getNearestCoordinateToTarget(head, reachableFood);

    List<MoveType> moves = new ArrayList<>();
    for (MoveType move : allowedMoves) {
      Coordinate nextMoveCoords = SnakeUtil.getNextMoveCoords(move, head);
      if (SnakeUtil.hasPath(board, body, nextMoveCoords, nearest)) {
        moves.add(move);
      }
    }

    if (moves.isEmpty()) {
      return MoveResponse.builder()
        .move(MoveType.LEFT.getValue())
        .build();
    }


    return MoveResponse.builder()
      .move(SnakeUtil.getNearestMoveToTarget(nearest, head, moves).getValue())
      .build();
  }
}
