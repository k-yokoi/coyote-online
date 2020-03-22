package net.kyokoi.coyote.entity;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
@Getter
public class Card implements Serializable {
    private int value;

    public Card(){
        this.value = Integer.MIN_VALUE;
    }

    public Card(int value){
        this.value = value;
    }
}
