package org.anonymous.card.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.anonymous.global.entities.BaseMemberEntity;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown=true)
public class TrainLog extends BaseMemberEntity {

    @Id @GeneratedValue
    private Long seq;

    private Long count;

    private boolean done;
}
