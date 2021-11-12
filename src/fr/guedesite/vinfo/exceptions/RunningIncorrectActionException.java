package fr.guedesite.vinfo.exceptions;

public class RunningIncorrectActionException extends Exception { 
    public RunningIncorrectActionException(String errorMessage) {
        super(errorMessage);
    }
}