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
        return board.getWidth() > coordinate.getX() && board.getHeight() > coordinate.getY();
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

        List<Coordinate> coordinateList = new ArrayList<>();
        List<MoveType> allowedMoves = new ArrayList<>();
        List<Coordinate> notAllowedCoordinates = new ArrayList<>();
        // All potential moves
        List<MoveType> moves = new ArrayList<>(EnumSet.allOf(MoveType.class));

        // Snake last move from the body list in order to get the starting position of the next move
        List<Coordinate> snakeBody = request.getYou().getBody();
        Coordinate lastLocation = snakeBody.get(snakeBody.size() - 1);
        Board board = request.getBoard();

        List<Snake> snakes = board.getSnakes();

        for (Snake snake : snakes) {
            for (Coordinate coordinate : snake.getBody()) {
                notAllowedCoordinates.add(coordinate);
                System.out.println(coordinate);
            }
        }

        for (MoveType move : moves) {
            Coordinate nextMove = getNextMoveCoords(move, lastLocation);
            if (isInBounds(board, nextMove) && !notAllowedCoordinates.contains(nextMove)) {
                allowedMoves.add(move);
            }
        }
        /* TODO
         * Given the move request, returns a list of all the moves that do not end in the snake dieing
         * Hint: finding all the coordinates leading to the snakes death and
         * comparing it to the potential moves is a good starting point
         * */
        return allowedMoves;
    }

    public static double getDistance(Coordinate first, Coordinate second) {
        return Math.sqrt(Math.pow(second.getX() - first.getX(), 2) + Math.pow(second.getY() - first.getY(), 2));
    }

    public static MoveType getNearestMoveToTarget(Coordinate target, Coordinate current, List<MoveType> moves) {
        /* TODO
         * Given the target coordinate, the current coordinate and a list of moves, returns
         * the nearest move to the target, selected from the moves list
         * */
        double targetDistance = getDistance(current,target);
        MoveType nearestMove = moves.get(0);
        for (MoveType move : moves) {
            if (getDistance(getNextMoveCoords(move, current),target)<targetDistance){
                nearestMove = move;
            }
        }
        return nearestMove;
    }

    public static Coordinate getNearestCoordinateToTarget(Coordinate target, List<Coordinate> coords) {
        /* TODO
         * Given the target coordinate and a list of coordinates, finds the nearest coordinate to the target
         * */
        Coordinate smallest = coords.get(0);
        for (Coordinate coord : coords) {
            for (Coordinate coord2 : coords) {
                if (getDistance(coord, target) < getDistance(coord, target)) {
                    smallest = coord;
                }
            }
        }
        return smallest;
    }
}
