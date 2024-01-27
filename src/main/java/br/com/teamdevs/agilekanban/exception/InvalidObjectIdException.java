package br.com.teamdevs.agilekanban.exception;

public class InvalidObjectIdException extends RuntimeException{
    public InvalidObjectIdException(String id) {
        super("Formato de ID invalido: " + id);
    }
}
