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

        List<Coordinate> coordinateList = new ArrayList<>();
        List<MoveType> allowedMoves = new ArrayList<>();
        List<Coordinate> notAllowedCoordinates = new ArrayList<>();
        // All potential moves
        List<MoveType> moves = new ArrayList<>(EnumSet.allOf(MoveType.class));

        // Snake last move from the body list in order to get the starting position of the next move
        List<Coordinate> snakeBody = request.getYou().getBody();
        Coordinate lastLocation = snakeBody.get(0);
        Board board = request.getBoard();

        for (Coordinate coordinate : snakeBody) {
            notAllowedCoordinates.add(coordinate);
        }
        List<Snake> snakes = board.getSnakes();

        for (Snake snake : snakes) {
            for (Coordinate coordinate : snake.getBody()) {
                notAllowedCoordinates.add(coordinate);
            }
        }

        for (MoveType move : moves) {
            Coordinate nextMove = getNextMoveCoords(move, lastLocation);
            if (isInBounds(board, nextMove) && !notAllowedCoordinates.contains(nextMove)) {
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

        for (MoveType move : moves) {
            double moveDistance = getDistance(getNextMoveCoords(move, current), target);
            for (MoveType move2 : moves) {
                double move2Distance = getDistance(getNextMoveCoords(move2, current), target);
                if (moveDistance < targetDistance && moveDistance < move2Distance) {
                    nearestMove = move;
                }
            }
        }
        return nearestMove;
    }

    public static Coordinate getNearestCoordinateToTarget(Coordinate target, List<Coordinate> coords) {
        /* TODO
         * Given the target coordinate and a list of coordinates, finds the nearest coordinate to the target
         * */
        Coordinate smallest = coords.get(0);
        double distanceSmallest = getDistance(coords.get(0), target);
        for (Coordinate coord : coords) {
            for (Coordinate coord2 : coords) {
                double distance = getDistance(coord, target);
                if (distance < getDistance(coord2, target) && distance < distanceSmallest) {
                    distanceSmallest = distance;
                    smallest = coord;
                }
            }
        }
        return smallest;
    }
}
