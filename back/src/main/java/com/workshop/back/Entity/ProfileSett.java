package com.workshop.back.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProfileSett {
    private String name;
    private String nickName;
    private String pricture;
}
