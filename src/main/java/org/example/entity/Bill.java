package org.example.entity;
import java.util.LinkedHashSet;
import java.util.Set;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.example.entity.enums.Status;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"billInfoID","AccountID"})
@Entity(name = "Bill")
@Table(name = "bill", schema = "shopaonam")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillID", nullable = false)
    private Integer billID;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AccountID")
    private Account accountID;

    @Column(name = "Ispaid",length = 1)
    private Integer paid;

    @OneToMany(mappedBy = "billID")
    @ToString.Exclude
    private Set<BillInfo> billinfos = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;


}