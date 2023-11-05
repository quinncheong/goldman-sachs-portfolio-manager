package com.is442g1t4.gpa.user;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Document(collection = "accessLog")
@Data
@AllArgsConstructor
@Entity
public class AccessLog {
    @Id
    private ObjectId id;
    private ObjectId userId;
    private String userName;
    private Date date;

    @Enumerated(EnumType.STRING)
    private Action action;
}


enum Action {
    LOGIN, LOGOUT, REGISTER, RESET_PASSWORD_PENDING, RESET_PASSWORD_SUCCESS, VERIFY_EMAIL
}
