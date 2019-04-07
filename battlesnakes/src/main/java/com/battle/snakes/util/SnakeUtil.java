package com.battle.snakes.util;


import com.battle.snakes.game.Board;
import com.battle.snakes.game.Coordinate;
import com.battle.snakes.game.MoveRequest;
import com.battle.snakes.game.MoveType;
import com.battle.snakes.game.Snake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;


public class SnakeUtil {

  private static final Random RANDOM = new Random();

  public static MoveType getRandomMove(List<MoveType> possibleMoves) {
    int randomIndex = RANDOM.nextInt(possibleMoves.size());
    return possibleMoves.get(randomIndex);
  }

  public static boolean isInBounds(Board board, Coordinate coordinate) {
    return board.getWidth() > coordinate.getX() &&
      coordinate.getX() >= 0 &&
      board.getHeight() > coordinate.getY() &&
      coordinate.getY() >= 0;
  }

  public static Coordinate getNextMoveCoords(MoveType moveType, Coordinate start) {
    int x = start.getX();
    int y = start.getY();

    switch (moveType) {
      case UP:
        y -= 1;
        break;
      case DOWN:
        y += 1;
        break;
      case LEFT:
        x -= 1;
        break;
      case RIGHT:
        x += 1;
        break;
    }

    return Coordinate
      .builder()
      .x(x)
      .y(y)
      .build();
  }

  public static List<MoveType> getAllowedMoves(MoveRequest request) {
    /* TODO
     * Given the move request, returns a list of all the moves that do not end in the snake dieing
     * Hint: finding all the coordinates leading to the snakes death and
     * comparing it to the potential moves is a good starting point
     * */
    List<Coordinate> coordinateList = new ArrayList<>();
    List<MoveType> allowedMoves = new ArrayList<>();

    // All potential moves
    List<MoveType> moves = new ArrayList<>(EnumSet.allOf(MoveType.class));

    Board board = request.getBoard();
    List<Coordinate> snakeBody = request.getYou().getBody();
    Coordinate lastLocation = snakeBody.get(0);

    List<Coordinate> snakes = getSnakesCoordinates(board, snakeBody);

    for (MoveType move : moves) {
      Coordinate nextMove = getNextMoveCoords(move, lastLocation);
      if (isInBounds(board, nextMove) && !snakes.contains(nextMove)) {
        allowedMoves.add(move);
      }
    }
    return allowedMoves;
  }

  public static double getDistance(Coordinate first, Coordinate second) {
    return Math.sqrt(Math.pow(second.getX() - first.getX(), 2) + Math.pow(second.getY() - first.getY(), 2));
  }

  public static MoveType getNearestMoveToTarget(Coordinate target, Coordinate current, List<MoveType> moves) {

    // Get distance between target and current coordinates to compare the next steps to
    double targetDistance = getDistance(current, target);
    MoveType nearestMove = moves.get(0);
    double nearestDistance = getDistance(getNextMoveCoords(moves.get(0), current), target);
    for (MoveType move : moves) {
      double moveDistance = getDistance(getNextMoveCoords(move, current), target);
      if (moveDistance < targetDistance && moveDistance < nearestDistance) {
        nearestMove = move;
        nearestDistance = moveDistance;
      }
    }
    return nearestMove;
  }

  public static Coordinate getNearestCoordinateToTarget(Coordinate target, List<Coordinate> coords) {
    Coordinate smallest = coords.get(0);
    double distanceSmallest = getDistance(coords.get(0), target);
    for (Coordinate coord : coords) {
      double distance = getDistance(coord, target);
      if (distance < distanceSmallest) {
        distanceSmallest = distance;
        smallest = coord;
      }
    }
    return smallest;
  }

  public static boolean hasPath(Board board, List<Coordinate> body, Coordinate current, Coordinate target) {

    List<Coordinate> snakes = getSnakesCoordinates(board, body);
    List<Coordinate> coordinateList = new ArrayList<>();
    List<Coordinate> visitedList = new ArrayList<>();

    coordinateList.add(current);

    // List of potential moves
    List<MoveType> moves = new ArrayList<>(EnumSet.allOf(MoveType.class));

    Coordinate coordinate = new Coordinate();

    while (!coordinateList.isEmpty()) {
      coordinate = coordinateList.get(0);
      coordinateList.remove(0);

      if (coordinate.getX() == target.getX() && coordinate.getY() == target.getY()) {
        return true;
      }

      for (MoveType move : moves) {
        Coordinate nextCoordinate = getNextMoveCoords(move, coordinate);
        if (!snakes.contains(nextCoordinate)
          && !visitedList.contains(nextCoordinate)
          && isInBounds(board, nextCoordinate)
          && !coordinateList.contains(nextCoordinate)) {
          coordinateList.add(nextCoordinate);
        }
      }
      visitedList.add(coordinate);
    }
    return false;
  }

  private static List<Coordinate> getSnakesCoordinates(Board board, List<Coordinate> body){
    List<Snake> snakes = board.getSnakes();
    List<Coordinate> snakesCoordinates = new ArrayList<>();

    for (Snake snake : snakes) {
      for (Coordinate coordinate : snake.getBody()) {
        snakesCoordinates.add(coordinate);
      }
    }
    for (Coordinate coordinate : body) {
      snakesCoordinates.add(coordinate);
    }
    return snakesCoordinates;
  }
}
