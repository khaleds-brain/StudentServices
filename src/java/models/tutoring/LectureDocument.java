/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.tutoring;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Ursina
 */
public class LectureDocument implements Serializable{
    private int lectureDocumentId;
    private String path;
    private String documentName;
    private Lecturer lecturer;
    private Timestamp lastUpdated;
    
    public LectureDocument(){
        this.lastUpdated = Timestamp.valueOf(LocalDateTime.now());
    }

    public int getLectureDocumentId() {
        return lectureDocumentId;
    }

    public void setLectureDocumentId(int lectureDocumentId) {
        this.lectureDocumentId = lectureDocumentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }
    
    public Timestamp getLastUpdated(){
        return lastUpdated;
    }
    
    public void setLastUpdated(Timestamp lastUpdated){
        this.lastUpdated = lastUpdated;
    }
}
