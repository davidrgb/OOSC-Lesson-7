package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.LinkedList;

import model.Bullet;
import model.Shooter;
import view.GameBoard;

public class TimerListener implements ActionListener {

    public enum EventType {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE
    }

    GameBoard gameBoard;
    private LinkedList<EventType> eventQueue;

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        processEventQueue();
        processCollision();
        gameBoard.getCanvas().repaint();
    }

    public void processEventQueue() {
        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return;

            switch (e) {
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if (shooter.canFireMoreBullet()) {
                        shooter.getWeapons().add(new Bullet(shooter.x, shooter.y));
                    }
                    break;
            }
        }
    }

    private void processCollision() {
        gameBoard.getShooter().removeBulletsOutOfBound();
    }

    private void update() {
        for (var e: gameBoard.getCanvas().getGameElements()) {
            e.animate();
        }
    }

    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }
    
}
