package me.zhengjie.mongoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;


@Document(collection = "dbori_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DboriStatustics implements Serializable {
    @Id
    private String id;
    private String cname;
    private int countd;
    private String datet;
    private double ssd;
    private String storages;
    private String weight;
}
