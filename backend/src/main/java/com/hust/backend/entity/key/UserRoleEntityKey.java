package com.hust.backend.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class UserRoleEntityKey implements Serializable {
    private String userId;
    private String roleId;
}
